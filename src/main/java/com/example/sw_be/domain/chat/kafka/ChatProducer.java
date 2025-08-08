package com.example.sw_be.domain.chat.kafka;

import com.example.sw_be.domain.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatProducer {
    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    public void send(ChatMessage msg) {
        kafkaTemplate.send("chat", msg.getChatRoomId().toString(), msg);
    }
}