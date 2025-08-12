package com.example.sw_be.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic 으로 시작하는 destination은 브로커(메모리)에서 처리
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 /app 으로 보내면 @MessageMapping 핸들러로 라우팅
        registry.setApplicationDestinationPrefixes("/app");
    }

}