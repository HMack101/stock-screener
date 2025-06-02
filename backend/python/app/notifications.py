import requests

HIGH_IMPACT_FORMS = {"10-K", "10-Q", "8-K", "S-1", "S-3", "DEF 14A"}
HIGH_IMPACT_KEYWORDS = ["earnings", "merger", "acquisition", "bankruptcy", "lawsuit", "offering", "CEO resignation"]

def is_high_impact_catalyst(catalyst_type, title):
    if catalyst_type == "filing":
        for form in HIGH_IMPACT_FORMS:
            if form in title:
                return True
    elif catalyst_type == "news":
        title_lower = title.lower()
        return any(keyword in title_lower for keyword in HIGH_IMPACT_KEYWORDS)
    return False

def send_webhook_notification(catalyst):
    if not WEBHOOK_URL:
        print("No webhook URL configured")
        return

    payload = {
        "ticker": catalyst.ticker,
        "type": catalyst.type,
        "title": catalyst.title,
        "url": catalyst.url,
        "timestamp": catalyst.timestamp.isoformat()
    }
    try:
        response = requests.post(WEBHOOK_URL, json=payload, timeout=5)
        response.raise_for_status()
        print(f"Webhook sent for {catalyst.ticker}: {catalyst.title}")
    except Exception as e:
        print(f"Failed to send webhook: {e}")

