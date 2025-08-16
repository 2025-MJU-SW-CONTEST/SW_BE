package com.example.sw_be.domain.movie.controller;

import com.example.sw_be.domain.movie.dto.MovieDetailResponse;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/init")
    public ResponseEntity<String> initMovies() {
        movieService.insertInitialMovies();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }


    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(movieService.getMovies(PageRequest.of(page, size)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailResponse> getMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieDetail(id));
    }


    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchMovies(@RequestParam String keyword) {
        return ResponseEntity.ok(movieService.searchMovies(keyword));
    }
}