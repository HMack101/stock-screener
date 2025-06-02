package com.hmack101.screener.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StockWebSocketController {

    @MessageMapping("/stocks")       // Client sends to: /app/stocks
    @SendTo("/topic/stocks")         // Server broadcasts to: /topic/stocks
    public String handleStockMessage(String message) throws Exception {
        // Here you can handle the message (e.g., parse ticker, fetch info, etc.)
        return "Received: " + message;
    }
}
