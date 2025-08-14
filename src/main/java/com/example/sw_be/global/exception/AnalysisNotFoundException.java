package com.example.sw_be.global.exception;

public class AnalysisNotFoundException extends RuntimeException {
    public AnalysisNotFoundException(Long id) {
        super("해당 해석 (ID: " + id + ")이 존재하지 않습니다.");
    }
}
