package com.example.sw_be.domain.movieGenre.entity;

import com.example.sw_be.domain.genre.entity.Genre;
import com.example.sw_be.domain.movie.entity.Movie;
import jakarta.persistence.*;

@Entity
public class MovieGenre {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
