package com.example.sw_be.domain.chat.service;

import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import com.example.sw_be.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageMongoService {
    private final ChatMessageRepository mongoRepo;

    /**
     * roomId에 해당하는 모든 채팅을 timestamp 오름차순으로 조회
     */
    public List<ChatMessageDocument> getChatHistory(Long roomId) {
        return mongoRepo.findByChatRoomIdOrderByTimestampAsc(roomId);
    }
}