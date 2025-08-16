package com.example.sw_be.domain.aiChat.controller;

import com.example.sw_be.domain.aiChat.service.AiChatService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "AI Chat", description = "AI 챗봇 관련 API")
public class AiChatController {
    private final AiChatService aiChatService;




    @Operation(
            summary = "AI 채팅 조회",
            description = "제목 검색 또는 일반 채팅을 조회합니다. " +
                    "제목 검색용 파라미터(k, title)가 존재하면 제목 검색을, " +
                    "그렇지 않으면 일반 채팅 조회를 수행합니다."
    )
    @GetMapping("/chats")
    public ResponseEntity<?> getChats(
            @Parameter(hidden = true) User user,
            @Parameter(description = "제목 검색 시 상위 k 번째 (0부터 시작합니다)", example = "0", required = false)
            @RequestParam(required = false) Long k, // 제목 검색용
            @Parameter(description = "검색할 제목 문자열", example = "어벤져스", required = false)
            @RequestParam(required = false) String title, // 제목 검색용
            @Parameter(description = "재목 조회 이후, 얘기하는 영화 아이디", example = "1", required = false)
            @RequestParam(required = false) Long movieId, // 평범한 채팅용
            @Parameter(description = "유저가 보내는 질문", example = "이게 뭐야?", required = false)
            @RequestParam(required = false) String text, // 평범한 채팅용
            @Parameter(description = "현재 AI 채팅룸 ID", example = "1", required = true)
            @RequestParam Long aiChatRoomId // 필수
    ) {

        if (k != null || (title != null && !title.isBlank())) {
            // 제목 검색 로직
            return ResponseEntity.ok(aiChatService.searchTitles(user, k, title, aiChatRoomId));
        } else {
            // 일반 채팅 조회 로직
            return ResponseEntity.ok(aiChatService.getChats(user, movieId, text, aiChatRoomId));
        }
    }


}
