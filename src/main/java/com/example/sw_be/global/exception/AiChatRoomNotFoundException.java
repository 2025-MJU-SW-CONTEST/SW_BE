package com.example.sw_be.global.exception;

public class AiChatRoomNotFoundException extends RuntimeException {
    public AiChatRoomNotFoundException(Long id) {
        super("존재하지 않는 AI 챗룸 입니다.(id"+ id+")");
    }


    public AiChatRoomNotFoundException(String message) {
        super(message);
    }
}
