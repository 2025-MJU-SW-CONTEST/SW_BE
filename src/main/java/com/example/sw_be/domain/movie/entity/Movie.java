package com.example.sw_be.domain.movie.entity;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.chatRoom.entity.ChatRoom;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import com.example.sw_be.domain.movieGenre.entity.MovieGenre;
import com.example.sw_be.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private Long id;
    private String title;
    @Column(length = 3000)
    private String summary;
    private Float rating;
    private Integer duration;
    private String thumbnailUrl;
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MovieCast> movieCasts = new ArrayList<>();


//    @OneToMany(mappedBy = "movie")
//    private List<MovieGenre> movieGenres = new ArrayList<>();

    @OneToOne(mappedBy = "movie")
    private ChatRoom chatRoom;

//    @OneToMany(mappedBy = "movie")
//    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Analysis> movieAnalysises= new ArrayList<>();

    public void addCast(MovieCast cast) {
        if (cast != null) {
            movieCasts.add(cast);
            cast.setMovie(this); // FK 세팅
        }
    }

    public void setMovieCasts(List<MovieCast> casts) {
        movieCasts.clear();
        if (casts != null) casts.forEach(this::addCast);
    }

}