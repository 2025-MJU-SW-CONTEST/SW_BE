package com.example.sw_be.domain.movie.service;

import com.example.sw_be.domain.movie.dto.MovieDetailResponse;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.repository.MovieRepository;
import com.example.sw_be.global.common.PageResponse;
import com.example.sw_be.global.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

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
}
