package com.example.sw_be.domain.movieGenre.repository;

import com.example.sw_be.domain.movieGenre.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenereRepository extends JpaRepository<MovieGenre, Long> {
}
