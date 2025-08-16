package com.example.sw_be.domain.chat.service;

import com.example.sw_be.domain.chat.dto.ChatMessage;
import com.example.sw_be.domain.chat.kafka.ChatProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatProducer producer;

    public void sendMessage(Long chatRoomId, Long userId, String text) {
        ChatMessage msg = new ChatMessage(chatRoomId, userId, text, LocalDateTime.now());
        producer.send(msg);
//        messagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, msg);
    }
}