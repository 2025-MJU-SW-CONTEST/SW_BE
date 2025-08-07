package com.example.sw_be.domain.chatRoom.entity;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.userChatRoom.entity.UserChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
