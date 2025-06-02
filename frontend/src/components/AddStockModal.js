import React, { useState } from "react";
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  Snackbar,
  Alert,
} from "@mui/material";
import axios from "axios";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  borderRadius: 2,
  boxShadow: 24,
  p: 4,
};

const AddStockModal = ({ onStockAdded }) => {
  const [open, setOpen] = useState(false);
  const [ticker, setTicker] = useState("");
  const [snackbar, setSnackbar] = useState({ open: false, type: "", message: "" });

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setTicker("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!ticker.trim()) return;

    try {
      const res = await axios.post(`http://localhost:8000/api/stocks/addOrUpdate/${ticker}`);
      setSnackbar({ open: true, type: "success", message: "Stock added!" });
      onStockAdded && onStockAdded(res.data);
      handleClose();
    } catch (err) {
      console.error("Add stock failed", err);
      setSnackbar({ open: true, type: "error", message: "Failed to add stock." });
    }
  };

  return (
    <>
      <Button variant="contained" color="primary" onClick={handleOpen}>
        + Add Stock
      </Button>

      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography variant="h6" component="h2" mb={2}>
            Add Stock
          </Typography>
          <form onSubmit={handleSubmit}>
            <TextField
              fullWidth
              label="Ticker"
              value={ticker}
              onChange={(e) => setTicker(e.target.value)}
              variant="outlined"
              margin="normal"
              required
            />
            <Box mt={2} display="flex" justifyContent="flex-end" gap={1}>
              <Button onClick={handleClose}>Cancel</Button>
              <Button type="submit" variant="contained" color="primary">
                Submit
              </Button>
            </Box>
          </form>
        </Box>
      </Modal>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      >
        <Alert
          severity={snackbar.type}
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          sx={{ width: "100%" }}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default AddStockModal;
