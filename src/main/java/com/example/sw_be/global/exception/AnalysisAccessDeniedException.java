package com.example.sw_be.global.exception;

public class AnalysisAccessDeniedException extends RuntimeException {
    public AnalysisAccessDeniedException(Long id) {
        super("해당 해석(id"+ id+")에 접근할 권한이 없습니다.");
    }

//    public AnalysisAccessDeniedException(String message) {
//        super(message);
//    }
}