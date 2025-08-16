package com.example.sw_be.domain.aiChatRoom.service;

import com.example.sw_be.domain.aiChatRoom.dto.AiChatRoomRes;
import com.example.sw_be.domain.user.entity.User;

public interface AiChatRoomService {
    AiChatRoomRes.NewAiChatRoom createNewAiChatRoom(User user);

    AiChatRoomRes.AiChatRoomHistory getAiChatRoomHistory(User user,Long aiChatRoomId, int page, int size);
}
