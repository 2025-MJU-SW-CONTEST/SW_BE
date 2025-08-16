package com.example.sw_be.global.exception;

public class DuplicateAnalysisException extends RuntimeException{
    public DuplicateAnalysisException(Long id) {
        super("해당 영화(ID: " + id + ") 해석을 이미 작성하였습니다.");
    }
}
