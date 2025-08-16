package com.example.sw_be.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chatMessages")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatMessageDocument {
    @Id
    private String id;
    private Long chatRoomId;
    private Long userId;
    private String message;
    private LocalDateTime timestamp;
}