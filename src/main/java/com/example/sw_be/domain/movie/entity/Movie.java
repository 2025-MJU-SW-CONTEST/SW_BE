package com.example.sw_be.domain.movie.entity;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.chatRoom.entity.ChatRoom;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import com.example.sw_be.domain.movieGenre.entity.MovieGenre;
import com.example.sw_be.domain.review.entity.Review;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String summary;
    private Float rating;
    private Integer duration;
    private String thumbnailUrl;
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie")
    private List<MovieCast> movieCasts = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @OneToOne(mappedBy = "movie")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "movie")
    private Analysis movieAnalysis;

}
