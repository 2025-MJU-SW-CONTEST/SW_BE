package com.example.sw_be.domain.movie.service;

import com.example.sw_be.domain.movie.dto.MovieDetailResponse;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.repository.MovieRepository;
import com.example.sw_be.global.common.PageResponse;
import com.example.sw_be.global.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;

    public Movie findById(long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    public Page<MovieResponse> getMovieList(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(MovieResponse::from);
    }

    public MovieDetailResponse getMovieDetail(Long id) {
        Movie movie = findById(id);
        return MovieDetailResponse.from(movie);
    }

    public Page<MovieResponse> searchMovies(String keyword, Pageable pageable) {
        return movieRepository
                .findByTitleContainingIgnoreCase(keyword, pageable)
                .map(MovieResponse::from);
    }
}
