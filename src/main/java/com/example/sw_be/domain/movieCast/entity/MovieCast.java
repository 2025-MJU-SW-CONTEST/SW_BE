package com.example.sw_be.domain.movieCast.entity;

import com.example.sw_be.domain.movie.entity.Movie;
import jakarta.persistence.*;

@Entity
public class MovieCast {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
