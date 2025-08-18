package com.example.sw_be.domain.aiChat.repository;

import com.example.sw_be.domain.aiChat.entity.AiChat;
import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiChatRepository extends JpaRepository<AiChat, Long> {
    Page<AiChat> findByAiChatRoom(AiChatRoom aiChatRoom, Pageable pageable);

    Page<AiChat> findByAiChatRoomId(Long aiChatRoomId, Pageable pageable);
}
