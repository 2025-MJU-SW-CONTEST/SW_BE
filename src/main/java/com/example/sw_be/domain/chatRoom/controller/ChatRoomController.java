package com.example.sw_be.domain.chatRoom.controller;

import com.example.sw_be.domain.chatRoom.entity.ChatRoom;
import com.example.sw_be.domain.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoom chatRoom) {
        ChatRoom saved = chatRoomService.createChatRoom(chatRoom);
        return ResponseEntity.ok(saved);
    }
}