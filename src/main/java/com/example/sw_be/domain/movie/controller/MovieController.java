package com.example.sw_be.domain.movie.controller;

import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "영화 관련 API")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "영화 목록 조회", description = "페이지 단위로 영화 리스트 조회")
    public ResponseEntity<Page<MovieResponse>> getMovieList(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(movieService.getMovies(pageable));
    }


}
