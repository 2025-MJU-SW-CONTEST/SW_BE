package com.example.sw_be.domain.movie.repository;

import com.example.sw_be.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Long> {
}
