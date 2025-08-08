package com.example.sw_be.domain.chat.kafka;

import com.example.sw_be.domain.chat.dto.ChatMessage;
import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import com.example.sw_be.domain.chat.repository.ChatMessageRepository;
import com.example.sw_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.sw_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatConsumer {
    private final ChatMessageRepository mongoRepo;
    private final ChatRoomRepository roomRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate ws;

    @KafkaListener(topics = "chat", groupId = "chat-group")
    public void listen(ChatMessage msg) {
        log.info("[ChatConsumer] 메시지 수신: chatRoomId={}, userId={}, message={}",
                msg.getChatRoomId(), msg.getUserId(), msg.getMessage());

        // 채팅방·사용자 유효성 검사
        roomRepo.findById(msg.getChatRoomId()).orElseThrow();
        userRepo.findById(msg.getUserId()).orElseThrow();

        // MongoDB에 저장
        ChatMessageDocument doc = ChatMessageDocument.builder()
                .chatRoomId(msg.getChatRoomId())
                .userId(msg.getUserId())
                .message(msg.getMessage())
                .timestamp(msg.getTimestamp())
                .build();
        mongoRepo.save(doc);

        // WebSocket 브로드캐스트
        ws.convertAndSend("/topic/chat/" + msg.getChatRoomId(), msg);
    }
}