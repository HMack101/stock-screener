import React, { useEffect, useState } from "react";
import { Alert, Snackbar, Typography } from "@mui/material";

export default function CatalystAlerts() {
  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:8000/ws/catalysts");

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setAlerts(prev => [data, ...prev].slice(0, 10)); // keep recent 10
    };

    return () => ws.close();
  }, []);

  return (
    <div>
      <Typography variant="h6" gutterBottom>
        Real-Time Catalyst Alerts
      </Typography>
      {alerts.map((alert, i) => (
        <Snackbar
          key={i}
          open={true}
          autoHideDuration={6000}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        >
          <Alert severity="warning" variant="filled">
            {alert.ticker}: {alert.data.title}
          </Alert>
        </Snackbar>
      ))}
    </div>
  );
}
