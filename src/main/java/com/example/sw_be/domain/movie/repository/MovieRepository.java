package com.example.sw_be.domain.movie.repository;

import com.example.sw_be.domain.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findAllByOrderByReleaseDateDesc(Pageable pageable);

    @Query("SELECT m FROM Movie m " +
            "WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Movie> searchMovies(@Param("keyword") String keyword);
}
