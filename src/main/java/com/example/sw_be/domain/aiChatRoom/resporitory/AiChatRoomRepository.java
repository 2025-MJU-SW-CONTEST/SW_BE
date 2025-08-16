package com.example.sw_be.domain.aiChatRoom.resporitory;

import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiChatRoomRepository extends JpaRepository<AiChatRoom, Long> {
}
