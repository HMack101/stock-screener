import aiohttp
import asyncio
import xml.etree.ElementTree as ET
import os


WEBHOOK_URL = os.getenv("WEBHOOK_URL")

async def fetch_filings(ticker: str, count: int = 5):
    cik_url = f"https://www.sec.gov/files/company_tickers.json"

    async with aiohttp.ClientSession() as session:
        async with session.get(cik_url) as resp:
            data = await resp.json()
            cik = None
            for record in data.values():
                if record['ticker'].upper() == ticker.upper():
                    cik = str(record['cik_str']).zfill(10)
                    break

            if not cik:
                return []

        feed_url = f"https://data.sec.gov/submissions/CIK{cik}.json"
        headers = {
            "User-Agent": "YourName Contact@YourCompany.com"
        }

        async with session.get(feed_url, headers=headers) as resp:
            company_data = await resp.json()
            filings = company_data.get("filings", {}).get("recent", {})
            results = []
            for i in range(min(count, len(filings["form"]))):
                results.append({
                    "form": filings["form"][i],
                    "filed_date": filings["filingDate"][i],
                    "accession_no": filings["accessionNumber"][i],
                    "url": f"https://www.sec.gov/Archives/edgar/data/{int(cik)}/{filings['accessionNumber'][i].replace('-', '')}/{filings['primaryDocument'][i]}"
                })
            return results

