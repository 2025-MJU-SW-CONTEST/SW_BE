package com.example.sw_be.domain.chatMessage.controller;

import com.example.sw_be.domain.chatMessage.dto.ChatMessageRequest;
import com.example.sw_be.domain.chatMessage.entity.ChatMessage;
import com.example.sw_be.domain.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageRequest request) {
        ChatMessage saved = chatMessageService.saveMessage(
                request.getChatroomId(),
                request.getSenderId(),
                request.getMessage()
        );
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(@RequestParam String chatroomId) {
        List<ChatMessage> messages = chatMessageService.getMessages(chatroomId);
        return ResponseEntity.ok(messages);
    }
}
