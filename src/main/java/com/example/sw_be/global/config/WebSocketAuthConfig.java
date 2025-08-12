package com.example.sw_be.global.config;

import com.example.sw_be.domain.user.repository.UserRepository;
import com.example.sw_be.global.auth.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSocketAuthConfig implements WebSocketMessageBrokerConfigurer {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor acc = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(acc.getCommand())) {
                    final String authHeader = acc.getFirstNativeHeader("Authorization");

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        log.warn("Missing or invalid Authorization header");
                        throw new MessagingException("Missing Authorization Bearer token");
                    }

                    final String token = authHeader.substring(7).trim();
                    if (token.isBlank()) {
                        log.warn("Empty JWT token");
                        throw new MessagingException("Empty JWT token");
                    }

                    if (jwtUtil.isExpired(token)) {
                        log.warn("Expired JWT token: {}", token);
                        throw new MessagingException("Expired JWT token");
                    }

                    final String email = jwtUtil.getUsername(token);
                    var userOpt = userRepository.findByEmail(email);
                    if (userOpt.isEmpty()) {
                        log.warn("User not found for email: {}", email);
                        throw new MessagingException("User not found");
                    }

                    var user = userOpt.get();
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null, List.of()
                    );
                    acc.setUser(auth);
                }

                return message;
            }
        });
    }
}

