package com.example.sw_be.domain.chat.repository;

import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

    // 초기 로드: T0(=before) 이전의 최근 N개 (정렬: 최신→과거)
    Page<ChatMessageDocument> findByChatRoomIdAndTimestampLessThanEqualOrderByTimestampDesc(
            Long chatRoomId, LocalDateTime before, Pageable pageable);

    // 스크롤 업: 가장 오래된 id 기준으로 더 과거 N개 (정렬: 최신→과거)
    Page<ChatMessageDocument> findByChatRoomIdAndIdLessThanOrderByIdDesc(
            Long chatRoomId, String id, Pageable pageable);
}
