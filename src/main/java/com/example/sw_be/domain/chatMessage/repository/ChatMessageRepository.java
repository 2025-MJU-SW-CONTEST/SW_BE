package com.example.sw_be.domain.chatMessage.repository;

import com.example.sw_be.domain.chatMessage.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatroomIdOrderByDateAsc(String chatroomId);
}