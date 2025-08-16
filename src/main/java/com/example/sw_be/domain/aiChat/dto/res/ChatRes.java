package com.example.sw_be.domain.aiChat.dto.res;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class ChatRes {

    @Data
    @Builder
    public static class Chat {
        String content;
        Long aiChatRoomId;
        LocalDateTime createdAt;

    }


    @Data
    @Builder
    public static class SearchTitle {
        Long movieId;
        String thumbnailUrl;
        Long aiChatRoomId;
        String response;


    }
}
