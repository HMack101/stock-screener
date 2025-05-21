import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";
import {
  Card,
  CardContent,
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Chip,
  CircularProgress,
  Box,
  TableSortLabel,
  TextField,
  InputAdornment,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

export default function ScreenerTable() {
  const [data, setData] = useState([]);
  const [sortBy, setSortBy] = useState("rotation");
  const [sortDirection, setSortDirection] = useState("desc");
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await axios.get("http://10.7.84.117:8000/api/screener");
      setData(res.data);
    } catch (err) {
      console.error("Failed to fetch screener data", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 60000); // auto-refresh every 60s
    return () => clearInterval(interval);
  }, []);

  const handleSort = (field) => {
    if (field === sortBy) {
      setSortDirection((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortBy(field);
      setSortDirection("desc");
    }
  };

  const sortedFilteredData = useMemo(() => {
    const filtered = data.filter((stock) =>
      stock.ticker.toLowerCase().includes(search.toLowerCase())
    );
    return filtered.sort((a, b) => {
      const aVal = a[sortBy];
      const bVal = b[sortBy];
      if (aVal === undefined || bVal === undefined) return 0;
      if (sortDirection === "asc") return aVal > bVal ? 1 : -1;
      return aVal < bVal ? 1 : -1;
    });
  }, [data, sortBy, sortDirection, search]);

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          Real-Time Screener
        </Typography>

        <TextField
          fullWidth
          margin="normal"
          placeholder="Search Ticker"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
        />

        {loading ? (
          <Box display="flex" justifyContent="center" p={4}>
            <CircularProgress />
          </Box>
        ) : (
          <Table size="small">
            <TableHead>
              <TableRow>
                {[
                  { key: "ticker", label: "Ticker" },
                  { key: "float", label: "Float" },
                  { key: "volume", label: "Volume" },
                  { key: "rotation", label: "Rotation" },
                  { key: "accumulating", label: "Accumulation" },
                  { key: "catalysts", label: "Catalysts" },
                  { key: "entry_exit", label: "Entry/Exit" },
                ].map((col) => (
                  <TableCell
                    key={col.key}
                    align={col.key === "ticker" ? "left" : "center"}
                    sortDirection={sortBy === col.key ? sortDirection : false}
                  >
                    {["ticker", "float", "volume", "rotation"].includes(col.key) ? (
                      <TableSortLabel
                        active={sortBy === col.key}
                        direction={sortDirection}
                        onClick={() => handleSort(col.key)}
                      >
                        {col.label}
                      </TableSortLabel>
                    ) : (
                      col.label
                    )}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {sortedFilteredData.map((stock) => (
                <TableRow key={stock.ticker}>
                  <TableCell>{stock.ticker}</TableCell>
                  <TableCell align="center">{stock.float.toLocaleString()}</TableCell>
                  <TableCell align="center">{stock.volume.toLocaleString()}</TableCell>
                  <TableCell align="center">
                    {stock.rotation?.toFixed(2)}x
                  </TableCell>
                  <TableCell align="center">
                    <Chip
                      label={stock.accumulating ? "Yes" : "No"}
                      color={stock.accumulating ? "success" : "default"}
                      size="small"
                    />
                  </TableCell>
                  <TableCell align="center">
                    <Chip label={stock.catalysts?.length || 0} size="small" color="info" />
                  </TableCell>
                  <TableCell>
                    {stock.entry_price && stock.exit_price ? (
                      <>
                        <strong>Buy:</strong> ${stock.entry_price} <br />
                        <strong>Sell:</strong> ${stock.exit_price}
                      </>
                    ) : (
                      <Typography variant="body2" color="textSecondary">
                        N/A
                      </Typography>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </CardContent>
    </Card>
  );
}

