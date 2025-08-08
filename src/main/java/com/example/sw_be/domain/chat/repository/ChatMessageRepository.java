package com.example.sw_be.domain.chat.repository;

import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);
}
