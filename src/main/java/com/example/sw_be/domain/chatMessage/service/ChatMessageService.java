package com.example.sw_be.domain.chatMessage.service;


import com.example.sw_be.domain.chatMessage.entity.ChatMessage;
import com.example.sw_be.domain.chatMessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(Integer chatroomId, Integer senderId, String message) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatroomId(chatroomId)
                .senderId(senderId)
                .message(message)
                .date(LocalDateTime.now())
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessages(String chatroomId) {
        return chatMessageRepository.findByChatroomIdOrderByDateAsc(chatroomId);
    }
}
