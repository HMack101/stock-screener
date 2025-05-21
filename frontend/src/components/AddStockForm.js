import React, { useState } from "react";
import axios from "axios";

const AddStockForm = ({ onStockAdded }) => {
  const [ticker, setTicker] = useState("");
  const [status, setStatus] = useState(null); // success | error | null

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!ticker.trim()) return;

    try {
      const res = await axios.post("http://10.7.84.117:8000/api/stock", {
        ticker: ticker.trim().toUpperCase(),
      });
      setStatus("success");
      setTicker("");
      if (onStockAdded) onStockAdded(res.data); // optional callback
    } catch (err) {
      console.error("Failed to add stock", err);
      setStatus("error");
    }

    setTimeout(() => setStatus(null), 3000); // auto-clear after 3s
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-4 rounded shadow mt-6">
      <h2 className="text-xl font-bold mb-3">Add Stock</h2>
      <div className="flex gap-2 items-center">
        <input
          type="text"
          value={ticker}
          onChange={(e) => setTicker(e.target.value)}
          placeholder="Enter ticker (e.g., AAPL)"
          className="border rounded px-3 py-2 w-full"
        />
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Add
        </button>
      </div>
      {status === "success" && (
        <p className="text-green-600 mt-2">Stock added successfully!</p>
      )}
      {status === "error" && (
        <p className="text-red-600 mt-2">Failed to add stock.</p>
      )}
    </form>
  );
};

export default AddStockForm;
