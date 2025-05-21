# polygon_service.py
import requests, os
from dotenv import load_dotenv

load_dotenv()
API_KEY = os.getenv("POLYGON_API_KEY")

def get_stock_data(ticker: str):
    url = f"https://api.polygon.io/v3/reference/tickers/{ticker}?apiKey={API_KEY}"
    ref = requests.get(url).json()
    float_val = ref['results']['share_class_shares_outstanding']

    url = f"https://api.polygon.io/v2/aggs/ticker/{ticker}/prev?adjusted=true&apiKey={API_KEY}"
    hist = requests.get(url).json()
    volume = hist['results'][0]['v']
    return float_val, volume


POLYGON_API_KEY = os.getenv("POLYGON_API_KEY")

def get_recent_news(ticker: str):
    url = f"https://api.polygon.io/v2/reference/news?ticker={ticker}&limit=5&apiKey={POLYGON_API_KEY}"
    response = requests.get(url)
    data = response.json()
    if "results" in data:
        return [
            {
                "title": item["title"],
                "published_utc": item["published_utc"],
                "url": item["article_url"]
            }
            for item in data["results"]
        ]
    return []

