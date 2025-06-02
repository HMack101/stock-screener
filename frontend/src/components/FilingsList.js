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

export default function FilingsList({ ticker }) {
  const [filings, setFilings] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8000/api/filings/${ticker}`)
      .then(res => setFilings(res.data.filings))
      .catch(console.error);
  }, [ticker]);

  return (
    <Card sx={{ mb: 3 }}>
      <CardContent>
        <Typography variant="h6">Filings for {ticker}</Typography>
        <List>
          {filings.map((f, i) => (
            <ListItem key={i}>
              <Link href={f.url} target="_blank" rel="noopener">
                {f.form} filed on {f.filed_date}
              </Link>
            </ListItem>
          ))}
        </List>
      </CardContent>
    </Card>
  );
}
