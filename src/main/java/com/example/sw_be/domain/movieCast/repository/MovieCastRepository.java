package com.example.sw_be.domain.movieCast.repository;

import com.example.sw_be.domain.movieCast.entity.MovieCast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCastRepository extends JpaRepository<MovieCast,Long> {

    List<MovieCast> findByMovieId(Long movieId);
}
