import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Card,
  CardContent,
  Typography,
  Link,
  List,
  ListItem,
} from "@mui/material";

export default function NewsList({ ticker }) {
  const [news, setNews] = useState([]);

  useEffect(() => {
    axios.get(`http://10.7.84.117:8000/api/news/${ticker}`)
      .then(res => setNews(res.data.news))
      .catch(console.error);
  }, [ticker]);

  return (
    <Card sx={{ mb: 3 }}>
      <CardContent>
        <Typography variant="h6">News for {ticker}</Typography>
        <List>
          {news.map((item, i) => (
            <ListItem key={i}>
              <Link href={item.url} target="_blank" rel="noopener">
                {item.title}
              </Link>{" "}
              — {new Date(item.published_utc).toLocaleString()}
            </ListItem>
          ))}
        </List>
      </CardContent>
    </Card>
  );
}
