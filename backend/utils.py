# stock_screener/utils.py
import httpx
import os
import datetime
from dotenv import load_dotenv

load_dotenv()
API_KEY = os.getenv("POLYGON_API_KEY")
BASE_URL = "https://api.polygon.io"

async def get_stock_quote(ticker: str):
    url = f"{BASE_URL}/v2/last/trade/{ticker}?apiKey={API_KEY}"
    async with httpx.AsyncClient() as client:
        resp = await client.get(url)
        return resp.json().get("results", {})

async def get_latest_news(ticker: str):
    url = f"{BASE_URL}/v2/reference/news?ticker={ticker}&apiKey={API_KEY}"
    async with httpx.AsyncClient() as client:
        resp = await client.get(url)
        return resp.json().get("results", [])

async def get_cik_from_ticker(ticker: str):
    url = "https://www.sec.gov/files/company_tickers.json"
    headers = {
        "User-Agent": "Your Name contact@yourdomain.com"
    }
    async with httpx.AsyncClient() as client:
        resp = await client.get(url, headers=headers)
        if resp.status_code != 200:
            return None
        mapping = resp.json()
        for _, info in mapping.items():
            if info["ticker"].lower() == ticker.lower():
                return str(info["cik_str"])
    return None

async def get_upcoming_filings(ticker: str):
    cik = await get_cik_from_ticker(ticker)
    if not cik:
        return []

    today = datetime.date.today()
    start_date = today - datetime.timedelta(days=7)

    url = f"https://data.sec.gov/submissions/CIK{cik.zfill(10)}.json"
    headers = {
        "User-Agent": "Your Name contact@yourdomain.com"
    }

    async with httpx.AsyncClient() as client:
        resp = await client.get(url, headers=headers)
        if resp.status_code != 200:
            return []

        filings = resp.json().get("filings", {}).get("recent", {})
        results = []
        for i in range(len(filings.get("filingDate", []))):
            filed_date = filings["filingDate"][i]
            if filed_date >= str(start_date):
                results.append({
                    "type": filings["form"][i],
                    "date": filed_date,
                    "description": filings["primaryDocDescription"][i],
                    "link": f"https://www.sec.gov/Archives/edgar/data/{int(cik)}/{filings['accessionNumber'][i].replace('-', '')}/{filings['primaryDocument'][i]}"
                })
        return results
