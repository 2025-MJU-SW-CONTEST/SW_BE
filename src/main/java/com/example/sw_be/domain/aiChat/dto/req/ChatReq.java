package com.example.sw_be.domain.aiChat.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatReq {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RagQueryRequest {
        private Long member_id;
        private Long movie_id;
        private String text;
        private Long chat_room_id;
    }
}
