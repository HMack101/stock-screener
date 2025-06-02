# 🧠 Stock Screener Backend (FastAPI)

This is the backend service powering the Real-Time Intraday Stock Screener. It handles data aggregation, float/volume calculations, signal generation, and caching.

## 🛠️ Features

- 🧮 Float rotation, unusual volume, catalyst signal detection
- ⚡ Real-time screener API (`/api/screener`)
- 📰 Ticker news endpoint (`/api/news/{ticker}`)
- 📋 Watchlist management with Redis caching
- 🔃 Auto-refresh logic on frontend via REST

## 📦 Tech Stack

- FastAPI
- SQLAlchemy (SQLite)
- Redis (caching)
- yfinance + polygon.io
- Uvicorn (ASGI server)

## 📂 Project Structure

app/
├── main.py # FastAPI app
├── models.py # SQLAlchemy models
├── database.py # DB session setup
├── cache.py # Redis setup
└── utils.py # Screener logic and helpers

shell
Copy
Edit

## 🔧 Setup Instructions

### 1. Install dependencies

```bash
pip install -r requirements.txt
```

### 2. Run Redis (locally or Docker)
```
docker run -p 6379:6379 redis
```

### 3. Start server
```
uvicorn app.main:app --reload
```
Default server: http://localhost:8000

## 📡 API Endpoints
Method	Endpoint	Description
GET	/api/screener	Returns live stock screener
GET	/api/news/{ticker}	Returns recent news for ticker
GET	/api/watchlist	Returns cached watchlist
POST	/api/stock	Adds a new stock to watchlist

### 🔐 Polygon.io API
Set your Polygon API key as an environment variable:
```
export POLYGON_API_KEY=your_key_here
```
Used to fetch real-time market data for multiple tickers.

### 📌 TODO
 - Add WebSocket for live updates
 - Add Catalyst/Event calendar sync
 - Integrate authentication for user-specific watchlists

---

Let me know if you want to combine both into a **monorepo README** or add diagrams (like API flow or DB schema).






