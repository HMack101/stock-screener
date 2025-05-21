import React, { useState } from "react";
import { Container, CssBaseline } from "@mui/material";
import StockList from "./components/StockList";
import NewsList from "./components/NewsList";
import FilingsList from "./components/FilingsList";
import CatalystAlerts from "./components/CatalystAlerts";
import ScreenerTable from "./components/ScreenerTable";
import AddStockForm from "./components/AddStockForm";
import AddStockModal from "./components/AddStockModal";

import { ThemeProvider } from "@mui/material/styles";
import theme from "./theme";

function App() {
  const [selectedTicker, setSelectedTicker] = useState(null);

  return (
    <Container maxWidth="md">
      <CssBaseline />
      <AddStockModal />
      <br />
      <ScreenerTable />
      <br />
      <StockList onSelectTicker={setSelectedTicker} />
      {selectedTicker && (
        <div className="max-w-4xl mx-auto p-4">
          <NewsList ticker={selectedTicker} />
          <FilingsList ticker={selectedTicker} />
        </div>
      )}      
      <CatalystAlerts />
    </Container>
  );
}

// In App.js:
<ThemeProvider theme={theme}>
  <App />
</ThemeProvider>

export default App;
