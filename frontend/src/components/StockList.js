import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Card,
  CardContent,
  Typography,
  List,
  ListItem,
  ListItemText,
  Divider,
} from "@mui/material";

export default function StockList({ onSelectTicker }) {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchStocklist = async () => {
    try {
      const res = await axios.get("http://localhost:8000/api/stocks");
      setStocks(res.data || []);
    } catch (err) {
      console.error("Failed to fetch watchlist", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStocklist();
    const interval = setInterval(fetchStocklist, 30000); // ⏱️ Refresh every 30s
    return () => clearInterval(interval);
  }, []);

  return (
    <Card sx={{ mb: 3 }}>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          Watchlist
        </Typography>
        <List>
          {stocks.map((s, index) => (
            <div key={s.id}>
              <ListItem button onClick={() => onSelectTicker(s.ticker)}>
                <ListItemText
                  primary={`${s.ticker}`}
                  secondary={`Float: ${s.floatShares} | Vol: ${s.avgVolume}`}
                />
              </ListItem>
              {index < stocks.length - 1 && <Divider />}
            </div>
          ))}
        </List>
      </CardContent>
    </Card>
  );
}
