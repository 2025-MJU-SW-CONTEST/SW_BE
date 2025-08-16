package com.example.sw_be.domain.aiChatRoom.dto;

import com.example.sw_be.domain.aiChat.entity.ChatRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class AiChatRoomRes {

    @Data
    @Builder
    public static class NewAiChatRoom {
        Long aiChatRoomId;
        String firstResponse;
        String secondResponse;
        LocalDateTime createdAt;
    }

    @Data
    @Builder
    public static class AiChatRoomHistory {
        Long aiChatRoomId;
        int size;
        int page;
        List<AiChatHistory> historyList;
    }

    @Data
    @Builder
    public static class AiChatHistory {
        Long aiChatId;
        ChatRole ChatRole;
        String contents;
        String createdAt;
    }
}
