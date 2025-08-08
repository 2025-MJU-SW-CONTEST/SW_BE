package com.example.sw_be.domain.chatroom.repository;

import com.example.sw_be.domain.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
