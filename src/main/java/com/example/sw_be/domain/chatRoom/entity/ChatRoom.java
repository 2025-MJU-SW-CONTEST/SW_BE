package com.example.sw_be.domain.chatRoom.entity;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;
}