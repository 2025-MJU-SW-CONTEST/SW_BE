package com.example.sw_be.domain.chatRoom.service;


import com.example.sw_be.domain.chatRoom.entity.ChatRoom;
import com.example.sw_be.domain.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final AdminClient adminClient;

    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

        String topicName = "chat-room-" + savedRoom.getId();
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);

        try {
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Kafka 토픽 생성 실패: " + topicName, e);
        }

        return savedRoom;
    }
}
   