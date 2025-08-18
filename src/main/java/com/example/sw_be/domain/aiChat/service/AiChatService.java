package com.example.sw_be.domain.aiChat.service;


import com.example.sw_be.domain.aiChat.dto.res.ChatRes;
import com.example.sw_be.domain.aiChat.entity.AiChat;
import com.example.sw_be.domain.aiChat.entity.ChatRole;
import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import com.example.sw_be.domain.user.entity.User;

public interface AiChatService {
    ChatRes.SearchTitle searchTitles(User user, Long k, String title, Long aiChatRoomId);

    ChatRes.Chat getChats(User user, Long movieId, String text, Long aiChatRoomId);

    AiChat createAndSaveChat(AiChatRoom aiChatRoom, ChatRole role, String contents);

    void addAnalysisToLLM(Long movieId, String text);
}
