package com.example.sw_be.global.exception;

public class NicknameDuplicateException extends RuntimeException{
    public NicknameDuplicateException(String newNick) {
        super(newNick +"은 이미 존재하는 닉네임입니다.");
    }
}
