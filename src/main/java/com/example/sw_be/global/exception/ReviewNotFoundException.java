package com.example.sw_be.global.exception;

import java.time.LocalDate;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(LocalDate date) {
        super( date + "에 감상평이 존재하지 않습니다.");
    }

    public ReviewNotFoundException(Long id) {
        super( "해당 감상평 (ID "+ id + ") 이 존재하지 않습니다.");
    }

}
