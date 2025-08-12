package com.example.sw_be.domain.chat.controller;

import com.example.sw_be.domain.chat.dto.ChatMessage;
import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import com.example.sw_be.domain.chat.service.ChatMessageMongoService;
import com.example.sw_be.domain.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {

    private final ChatService chatService;
    private final ChatMessageMongoService mongoService;

    /**
     * 클라이언트에서 채팅 전송(POST)할 때 사용
     */
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessage msg) {
        chatService.sendMessage(msg.getChatRoomId(), msg.getUserId(), msg.getMessage());
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/send/{roomId}")
    @Operation(summary = "소켓 통신 채팅")
    public void sendMessageSocket(@DestinationVariable String roomId, @Payload ChatMessage msg) {
        chatService.sendMessage(Long.parseLong(roomId), msg.getUserId(), msg.getMessage());
    }

    /**
     * 특정 채팅방(roomId)의 MongoDB 저장 채팅 내역 조회(GET)
     */
    @GetMapping("/history/{roomId}")
    public ResponseEntity<List<ChatMessageDocument>> getHistory(@PathVariable Long roomId) {
        List<ChatMessageDocument> history = mongoService.getChatHistory(roomId);
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(history);
    }
}