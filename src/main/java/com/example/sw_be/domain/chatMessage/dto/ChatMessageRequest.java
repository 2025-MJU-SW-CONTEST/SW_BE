package com.example.sw_be.domain.chatMessage.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequest {
    private Integer chatroomId;
    private Integer senderId;
    private String message;
}
