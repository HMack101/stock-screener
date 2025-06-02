# models.py
from sqlalchemy import Column, Integer, String, Float, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import DateTime, ForeignKey
from sqlalchemy.orm import relationship
from datetime import datetime

Base = declarative_base()

class Stock(Base):
    __tablename__ = "stocks"
    id = Column(Integer, primary_key=True)
    ticker = Column(String, unique=True)
    float = Column(Float)
    avg_volume = Column(Float)



class Catalyst(Base):
    __tablename__ = "catalysts"
    id = Column(Integer, primary_key=True, index=True)
    ticker = Column(String, index=True)
    type = Column(String)  # "news" or "filing"
    title = Column(String)
    url = Column(String)
    timestamp = Column(DateTime, default=datetime.utcnow)
    is_high_impact = Column(Boolean, default=False)

