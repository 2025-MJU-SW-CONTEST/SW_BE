package com.example.sw_be.domain.movieCast.repository;

import com.example.sw_be.domain.movieCast.entity.MovieCast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCastRepository extends JpaRepository<MovieCast,Long> {

}
