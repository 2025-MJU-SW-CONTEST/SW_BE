package com.example.sw_be.domain.chat.service;

import com.example.sw_be.domain.chat.entity.ChatMessageDocument;
import com.example.sw_be.domain.chat.repository.ChatMessageRepository;
import com.example.sw_be.global.common.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageMongoService {
    private final ChatMessageRepository mongoRepo;

    public List<ChatMessageDocument> getRecentHistory(Long roomId) {
        var page = mongoRepo.findByChatRoomIdAndTimestampLessThanEqualOrderByTimestampDesc(
                roomId, LocalDateTime.now(), PageUtil.chatPage());
        var list = new ArrayList<>(page.getContent());
        Collections.reverse(list);
        return list;
    }

    public List<ChatMessageDocument> getBeforeByChatId(Long roomId, String chatId) {
        var page = mongoRepo.findByChatRoomIdAndIdLessThanOrderByIdDesc(
                roomId, chatId, PageUtil.chatPage());
        var list = new ArrayList<>(page.getContent());
        Collections.reverse(list);
        return list;
    }

}