package com.example.sw_be.domain.aiChat.entity;

import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AiChatRoom aiChatRoom;

    @Enumerated(EnumType.STRING)
    private ChatRole chatRole;

    @Column(length = 2000)
    private String contents;


    private LocalDateTime createdAt;

}
