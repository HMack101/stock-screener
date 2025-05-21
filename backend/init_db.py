import asyncio
from app.db import engine
from app.models import Base

async def init_models():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.drop_all)
        await conn.run_sync(Base.metadata.create_all)

if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(init_models())

