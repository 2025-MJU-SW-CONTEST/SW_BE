package com.example.sw_be.global.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("해당 영화(ID: " + id + ")가 존재하지 않습니다.");
    }
}