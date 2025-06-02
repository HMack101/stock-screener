package com.hmack101.screener.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // client subscribes to /topic/stocks
        config.setApplicationDestinationPrefixes("/app"); // client sends to /app/stocks
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/stocks")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Enables fallback options like long polling
    }
}
