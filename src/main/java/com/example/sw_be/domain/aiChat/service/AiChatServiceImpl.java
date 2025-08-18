package com.example.sw_be.domain.aiChat.service;

import com.example.sw_be.domain.aiChat.dto.req.ChatReq;
import com.example.sw_be.domain.aiChat.dto.req.ChatReq.RagQueryRequest;
import com.example.sw_be.domain.aiChat.dto.res.ChatRes;
import com.example.sw_be.domain.aiChat.dto.res.SearchTitles;
import com.example.sw_be.domain.aiChat.entity.AiChat;
import com.example.sw_be.domain.aiChat.entity.ChatRole;
import com.example.sw_be.domain.aiChat.repository.AiChatRepository;
import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import com.example.sw_be.domain.aiChatRoom.resporitory.AiChatRoomRepository;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.repository.MovieRepository;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.global.exception.AiChatRoomNotFoundException;
import com.example.sw_be.global.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.example.sw_be.domain.aiChat.dto.req.ChatReq.*;


@Service
@Transactional
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {
    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;
    private final AiChatRepository aiChatRepository;
    private final AiChatRoomRepository aiChatRoomRepository;

    @Override
    public ChatRes.SearchTitle searchTitles(User user, Long k, String title, Long aiChatRoomId) {
        // FastAPI URL
        String url = String.format("http://13.125.70.2:8000/search?title=%s&k=%d", title, k);


        ResponseEntity<SearchTitles> response =
                restTemplate.getForEntity(url, SearchTitles.class);

        SearchTitles.Result result = response.getBody().getResult();

        if (result == null) {
            throw new RuntimeException("FastAPI 응답에 result가 없습니다.");
        }

        Long movieId = result.getMovie_id();
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        AiChatRoom aiChatRoom = aiChatRoomRepository.findById(aiChatRoomId).orElseThrow(() -> new AiChatRoomNotFoundException(aiChatRoomId));


        createAndSaveChat(aiChatRoom, ChatRole.USER, title);
        createAndSaveChat(aiChatRoom, ChatRole.AI, "이 영화를 말씀하시는 걸까요?");
        createAndSaveChat(aiChatRoom, ChatRole.AI, movie.getThumbnailUrl());


        return ChatRes.SearchTitle.builder()
                .movieId(movieId)
                .thumbnailUrl(movie.getThumbnailUrl())
                .aiChatRoomId(aiChatRoomId)
                .response("이 영화를 말씀하시는 걸까요?")
                .build();
    }

    @Override
    public ChatRes.Chat getChats(User user, Long movieId, String text, Long aiChatRoomId) {
        if (!movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }
        if (!aiChatRoomRepository.existsById(aiChatRoomId)) {
            throw new AiChatRoomNotFoundException(aiChatRoomId);
        }


        // FastAPI URL
        String url = "http://13.125.70.2:8000/rag/query";

        // 요청 body 생성
        RagQueryRequest requestBody = new RagQueryRequest(
                user.getUserid(),
                movieId,
                text,
                aiChatRoomId
        );

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RagQueryRequest> request = new HttpEntity<>(requestBody, headers);

        // POST 요청 보내기
        String responseStr = restTemplate.postForObject(url, request, String.class);

        //채팅 기록 저장
        AiChatRoom aiChatRoom = aiChatRoomRepository.findById(aiChatRoomId).orElseThrow(() -> new AiChatRoomNotFoundException(aiChatRoomId));


        createAndSaveChat(aiChatRoom, ChatRole.USER, text);
        createAndSaveChat(aiChatRoom, ChatRole.AI, responseStr);

        // Chat 객체로 변환 후 반환
        return ChatRes.Chat.builder()
                .content(responseStr)
                .aiChatRoomId(aiChatRoomId)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Transactional
    public AiChat createAndSaveChat(AiChatRoom aiChatRoom, ChatRole role, String contents) {
        AiChat chat = AiChat.builder()
                .aiChatRoom(aiChatRoom)
                .chatRole(role)
                .contents(contents)
                .createdAt(LocalDateTime.now())
                .build();

        return aiChatRepository.save(chat);
    }

    @Override
    @Transactional
    public void addAnalysisToLLM(Long movieId, String text) {
        // FastAPI URL
        String url = "http://13.125.70.2:8000/rag/analysis";

        // 요청 body 생성
        AddAnalysisToLLM requestBody = new AddAnalysisToLLM(movieId, text);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AddAnalysisToLLM> request = new HttpEntity<>(requestBody, headers);

        // POST 요청 보내기
        String result = restTemplate.postForObject(url, request, String.class);

    }

}

