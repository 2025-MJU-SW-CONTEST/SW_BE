package com.example.sw_be.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long chatRoomId;
    private Long userId;
    private String message;
    private LocalDateTime timestamp;
}