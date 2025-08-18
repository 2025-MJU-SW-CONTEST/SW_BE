package com.example.sw_be.domain.aiChat.controller;

import com.example.sw_be.domain.aiChat.dto.req.ChatReq;
import com.example.sw_be.domain.aiChat.service.AiChatService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/chats/{aiChatRoom-Id}")
    public ResponseEntity<?> getChats(
            @Parameter(hidden = true) User user,
            @RequestBody ChatReq.ChatRequestDTO request,
            @PathVariable(value = "aiChatRoom-Id") Long aiChatRoomId // 필수
    ) {

        if (request.getK() != null || (request.getTitle() != null && !request.getTitle().isBlank())) {
            // 제목 검색 로직
            return ResponseEntity.ok(aiChatService.searchTitles(user, request.getK(), request.getTitle(), aiChatRoomId));
        } else {
            // 일반 채팅 조회 로직
            return ResponseEntity.ok(aiChatService.getChats(user, request.getMovieId(), request.getText(), aiChatRoomId));
        }
    }


}
