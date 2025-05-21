from fastapi import FastAPI, WebSocket, Depends
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from pydantic import BaseModel
import requests
import os
import asyncio
from dotenv import load_dotenv
from sqlalchemy.ext.asyncio import create_async_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column, Integer, String, Float
from sqlalchemy.ext.declarative import declarative_base
from apscheduler.schedulers.asyncio import AsyncIOScheduler
from apscheduler.triggers.cron import CronTrigger
from app.models import Stock
from app.polygon_service import get_stock_data
from app.edgar_service import fetch_filings
from fastapi.websockets import WebSocketDisconnect
from collections import defaultdict
import time
from app.models import Catalyst, Stock
from app.polygon_service import get_recent_news
from app.edgar_service import fetch_filings
from sqlalchemy import select
from datetime import datetime
from sqlalchemy.exc import IntegrityError
import yfinance as yf
from fastapi import HTTPException



clients = set()
last_sent_news = defaultdict(lambda: 0)


load_dotenv()

# Database Setup
DATABASE_URL = os.getenv("DATABASE_URL", "postgresql+asyncpg://user:pass@localhost/stockdb")
engine = create_async_engine(DATABASE_URL, echo=True)
SessionLocal = sessionmaker(bind=engine, class_=AsyncSession, expire_on_commit=False)

Base = declarative_base()

class Stock(Base):
    __tablename__ = "stocks"
    id = Column(Integer, primary_key=True)
    ticker = Column(String, unique=True)
    float = Column(Float)
    avg_volume = Column(Float)

class StockRequest(BaseModel):
    ticker: str


# FastAPI App
app = FastAPI()

origins = [ 'http://localhost:3000', '*' ]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Dependency
async def get_db():
    async with SessionLocal() as session:
        yield session

async def refresh_float_and_volume():
    async with SessionLocal() as db:
        result = await db.execute(select(Stock))
        stocks = result.scalars().all()

        for stock in stocks:
            try:
                float_val, volume = get_stock_data(stock.ticker)
                stock.float = float_val
                stock.avg_volume = volume
            except Exception as e:
                print(f"Failed to update {stock.ticker}: {e}")
        await db.commit()



async def fetch_and_store_catalysts():
    async with SessionLocal() as db:
        result = await db.execute(select(Stock.ticker))
        tickers = result.scalars().all()

        for ticker in tickers:
            # NEWS
            try:
                news_items = get_recent_news(ticker)
                for item in news_items:
                    timestamp = datetime.strptime(item["published_utc"], "%Y-%m-%dT%H:%M:%SZ")
                    exists = await db.execute(
                        select(Catalyst).where(Catalyst.ticker == ticker, Catalyst.url == item["url"])
                    )
                    if not exists.scalars().first():
                        db.add(Catalyst(
                            ticker=ticker,
                            type="news",
                            title=item["title"],
                            url=item["url"],
                            timestamp=timestamp
                        ))
                        if is_high_impact_catalyst("news", item["title"]):
                            send_webhook_notification(catalyst_object)
                        if is_high_impact_catalyst("filing", f"{f['form']} filed on {f['filed_date']}"):
                            send_webhook_notification(catalyst_object)
            except Exception as e:
                print(f"Error fetching news for {ticker}: {e}")

            # FILINGS
            try:
                filings = await fetch_filings(ticker)
                for f in filings:
                    exists = await db.execute(
                        select(Catalyst).where(Catalyst.ticker == ticker, Catalyst.url == f["url"])
                    )
                    if not exists.scalars().first():
                        db.add(Catalyst(
                            ticker=ticker,
                            type="filing",
                            title=f"{f['form']} filed on {f['filed_date']}",
                            url=f["url"],
                            timestamp=datetime.strptime(f["filed_date"], "%Y-%m-%d")
                        ))
                        if is_high_impact_catalyst("news", item["title"]):
                            send_webhook_notification(catalyst_object)
                        if is_high_impact_catalyst("filing", f"{f['form']} filed on {f['filed_date']}"):
                            send_webhook_notification(catalyst_object)
            except Exception as e:
                print(f"Error fetching filings for {ticker}: {e}")

        await db.commit()


# Polygon API Integration
POLYGON_API_KEY = os.getenv("POLYGON_API_KEY")

def get_stock_data(ticker: str):
    url = f"https://api.polygon.io/v3/reference/tickers/{ticker}?apiKey={POLYGON_API_KEY}"
    ref = requests.get(url).json()
    float_val = 0
    volume = 0 
    if 'results' in ref:
    	float_val = ref['results']['share_class_shares_outstanding']

    url = f"https://api.polygon.io/v2/aggs/ticker/{ticker}/prev?adjusted=true&apiKey={POLYGON_API_KEY}"
    if 'results' in ref: 
        hist = requests.get(url).json()
        volume = hist['results'][0]['v']

    return float_val, volume

#async def get_stock_data(ticker: str):
#    try:
#        stock_info = yf.Ticker(ticker).info
#        float_val = stock_info.get("floatShares", 0) or 0
#        avg_volume = stock_info.get("averageVolume", 0) or 0
#        return float_val, avg_volume
#    except Exception as e:
#        # Log the error and return default or raise custom error
#        print(f"Failed to fetch data for {ticker}: {e}")
#        return 0, 0



# Helper Functions
def calculate_float_rotation(volume: int, float_shares: float) -> float:
    if float_shares == 0:
        return 0.0
    return round(volume / float_shares, 2)

def detect_unusual_volume(volume: int, avg_volume: int) -> bool:
    return volume > 2 * avg_volume

def suggest_entry_exit(volume: int, avg_volume: int, float_val: float) -> str:
    if detect_unusual_volume(volume, avg_volume) and calculate_float_rotation(volume, float_val) > 2:
        return "High volume + float rotation. Consider watching for breakout or scalp."
    return "No strong signal. Monitor price action."



@app.on_event("startup")
async def startup_event():
    scheduler = AsyncIOScheduler()
    scheduler.add_job(refresh_float_and_volume, CronTrigger(hour=6, minute=0))  # runs daily at 6:00 AM
    scheduler.start()
    scheduler.add_job(fetch_and_store_catalysts, CronTrigger(minute="*/15"))  # every 15 minutes



# API Endpoints
@app.get("/api/screener")
async def get_screener(db: AsyncSession = Depends(get_db)):
    results = []
    stocks = await db.execute(select(Stock))
    tickers = []
    for stock in stocks.scalars():
        float_val, volume = get_stock_data(stock.ticker)
        result = await db.execute(select(Stock).where(Stock.ticker == stock.ticker))
        data = result.scalars().first()

        if data:
            if float_val != 0:
                data.float = float_val
            if volume != 0:
                data.avg_volume = volume
        else:
            data = Stock(ticker=stock.ticker, float=float_val, avg_volume=volume)
            db.add(stock)

        await db.commit()

        suggestion = suggest_entry_exit(volume, stock.avg_volume, float_val)
        results.append({
            "ticker": stock.ticker,
            "float": float_val,
            "volume": volume,
            "float_rotation": calculate_float_rotation(volume, float_val),
            "unusual_volume": detect_unusual_volume(volume, stock.avg_volume),
            "entry_exit_suggestion": suggestion
        })
    return results



@app.get("/api/health")
def health_check():
    return {"status": "ok"}


# WebSocket Placeholder
@app.websocket("/ws/stocks")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    try:
        while True:
            data = await websocket.receive_text()
            # Here you can parse `data` if needed
            await websocket.send_text(f"Received: {data}")
    except WebSocketDisconnect:
        print("Client disconnected")




@app.get("/api/stocks")
async def list_stocks(db: AsyncSession = Depends(get_db)):
    result = await db.execute(select(Stock))
    stocks = result.scalars().all()
    return [{ 'ticker': s.ticker , 'float': s.float, 'avg_volume': s.avg_volume } for s in stocks]




@app.post("/api/stocks")
async def add_or_update_stock(request: StockRequest, db: AsyncSession = Depends(get_db)):
    ticker = request.ticker.upper()
    try:
        float_val, volume = get_stock_data(ticker)  # ✅ use await here

        result = await db.execute(select(Stock).where(Stock.ticker == ticker))
        stock = result.scalars().first()

        if stock:
            if float_val != 0:
                stock.float = float_val
            if volume != 0:
                stock.avg_volume = volume
            msg = f"Updated {ticker} successfully"
        else:
            stock = Stock(ticker=ticker, float=float_val, avg_volume=volume)
            db.add(stock)
            msg = f"Added {ticker} successfully"

        await db.commit()
        return {"message": msg}

    except IntegrityError:
        await db.rollback()
        raise HTTPException(status_code=400, detail=f"Ticker {ticker} already exists.")
    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=f"Failed to process {ticker}: {str(e)}")




@app.get("/api/news/{ticker}")
async def get_news(ticker: str):
    try:
        news_items = get_recent_news(ticker.upper())
        return {"ticker": ticker.upper(), "news": news_items}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))



@app.get("/api/filings/{ticker}")
async def get_filings(ticker: str):
    try:
        filings = await fetch_filings(ticker)
        return {"ticker": ticker.upper(), "filings": filings}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/api/catalysts/{ticker}")
async def get_stored_catalysts(ticker: str, db: AsyncSession = Depends(get_db)):
    result = await db.execute(
        select(Catalyst).where(Catalyst.ticker == ticker.upper()).order_by(Catalyst.timestamp.desc()).limit(20)
    )
    rows = result.scalars().all()
    return [
        {
            "type": row.type,
            "title": row.title,
            "url": row.url,
            "timestamp": row.timestamp.isoformat()
        }
        for row in rows
    ]



