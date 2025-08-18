package com.example.sw_be.domain.aiChat.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddAnalysisToLLM {
        private Long movie_id;
        private String text;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRequestDTO {
        @Schema(description = "제목 검색 시 상위 k 번째 (0부터 시작합니다)", example = "0", required = false)
        private Long k;
        @Schema(description = "검색할 제목 문자열", example = "어벤져스", required = false)
        private String title;
        @Schema(description = "제목 조회 이후, 얘기하는 영화 아이디", example = "1", required = false)
        private Long movieId;
        @Schema(description = "유저가 보내는 질문", example = "이게 뭐야?", required = false)
        private String text;
        @Schema(description = "현재 AI 채팅룸 ID", example = "1", required = true)
        private Long aiChatRoomId;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addAnalysis {
        private Long movie_id;
        private String text;
    }
}
