package com.example.sw_be.domain.movie.controller;

import com.example.sw_be.domain.movie.dto.MovieDetailResponse;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.service.MovieService;
import com.example.sw_be.global.common.PageResponse;
import com.example.sw_be.global.common.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "영화 관련 API")

public class MovieController {

    private final MovieService movieService;


    @GetMapping("/init")
    public ResponseEntity<String> initMovies() {
        movieService.insertInitialMovies();
        return ResponseEntity.ok("영화 데이터 저장 성공");
    }

    @GetMapping("/list")
    @Operation(summary = "영화 리스트 조회", description = "페이지 단위로 영화 리스트 조회")
    public ResponseEntity<PageResponse<MovieResponse>> getMovieList(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Page<MovieResponse> movies = movieService.getMovieList(PageUtil.defaultPage(page, size, "releaseDate"));
        return ResponseEntity.ok(new PageResponse<>(movies));
    }

    @GetMapping("/{id}")
    @Operation(summary = "영화 상세 조회", description = "영화 ID로 영화 상세 조회")
    public ResponseEntity<MovieDetailResponse> getMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieDetail(id));
    }

    @GetMapping("/search")
    @Operation(summary = "영화 검색", description = "제목 키워드로 영화 검색 (페이지 단위)")
    public ResponseEntity<PageResponse<MovieResponse>> searchMovies(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MovieResponse> movies = movieService.searchMovies(keyword, PageUtil.noSortPage(page, size));
        return ResponseEntity.ok(new PageResponse<>(movies));
    }

}

