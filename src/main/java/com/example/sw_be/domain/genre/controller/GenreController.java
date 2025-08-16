package com.example.sw_be.domain.genre.controller;

import com.example.sw_be.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final MovieService genreService;


    @GetMapping("/init")
    public ResponseEntity<String> initMovies() {
        genreService.insertInitialMovies();
        return ResponseEntity.ok("성공적으로 장르 리스트가 저장되었습니다.");
    }
}
