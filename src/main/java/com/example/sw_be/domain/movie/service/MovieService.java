package com.example.sw_be.domain.movie.service;

import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.repository.MovieRepository;
import com.example.sw_be.global.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie findByid(long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }


}
