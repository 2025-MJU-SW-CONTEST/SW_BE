package com.example.sw_be.domain.aiChatRoom.cotroller;

import com.example.sw_be.domain.aiChatRoom.dto.AiChatRoomRes;
import com.example.sw_be.domain.aiChatRoom.service.AiChatRoomService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AI Chat Room", description = "AI 챗봇 채팅창 관련 API")
@RequestMapping("/api/aiChatRooms")
public class AiChatRoomController {
    private final AiChatRoomService aiChatRoomService;


    @Operation(
            summary = "새로운 AI 채팅방 생성",
            description = "새로운 AI 챗봇 채팅방을 생성합니다."
    )
    @PostMapping("/new")
    public ResponseEntity<AiChatRoomRes.NewAiChatRoom> createNewAiChatRoom(@Parameter(hidden = true) User user) {
        return ResponseEntity.ok(aiChatRoomService.createNewAiChatRoom(user));
    }

    @Operation(
            summary = "AI 채팅 히스토리 조회",
            description = "요청하는 챗봇 채팅방의 히스토리를 페이지로 조회합니다."
    )
    @GetMapping("{aiChatRoom-Id}/history")
    public ResponseEntity<AiChatRoomRes.AiChatRoomHistory> getChatRoomHistory(@Parameter(hidden = true) User user,
                                                                              @Parameter(description = "조회하는 ai 채팅방 아이디", example = "0")
                                                                              @PathVariable(value = "aiChatRoom-Id") Long aiChatRoomId,
                                                                              @Parameter(description = "요청 페이지 (0부터 시작합니다.)", example = "0")
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @Parameter(description = "페이지당 채팅 수 (기본 30개)", example = "30", required = false)
                                                                              @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(aiChatRoomService.getAiChatRoomHistory(user,aiChatRoomId, page, size));
    }




}
