package com.example.sw_be.domain.movie.entity;

import com.example.sw_be.domain.chatroom.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Float score;
    private Integer duration;
    private String thumbnail;
    private LocalDate date;

    @OneToMany(mappedBy = "movie")
    private List<ChatRoom> chatRooms;
}