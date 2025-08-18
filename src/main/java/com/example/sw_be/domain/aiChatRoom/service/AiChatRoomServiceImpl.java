package com.example.sw_be.domain.aiChatRoom.service;

import com.example.sw_be.domain.aiChat.entity.AiChat;
import com.example.sw_be.domain.aiChat.entity.ChatRole;
import com.example.sw_be.domain.aiChat.repository.AiChatRepository;
import com.example.sw_be.domain.aiChat.service.AiChatService;
import com.example.sw_be.domain.aiChatRoom.dto.AiChatRoomRes;
import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import com.example.sw_be.domain.aiChatRoom.resporitory.AiChatRoomRepository;
import com.example.sw_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AiChatRoomServiceImpl implements AiChatRoomService{
    private final AiChatRoomRepository aiChatRoomRepository;
    private final AiChatRepository aiChatRepository;
    private final AiChatService aiChatService;

    @Override
    @Transactional
    public AiChatRoomRes.NewAiChatRoom createNewAiChatRoom(User user) {
        AiChatRoom newAiChatRoom = AiChatRoom.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        AiChatRoom AiChatRoom = aiChatRoomRepository.save(newAiChatRoom);

        aiChatService.createAndSaveChat(AiChatRoom, ChatRole.AI, "안녕하세요! 저는 필리, 여러분과 함께\n" +
                "영화의 숨겨진 의미를 찾아가는 친구에요.\n" +
                "궁금한 장면이나 감독의 의도가 있다면 편하게 물어보세요!");
        aiChatService.createAndSaveChat(AiChatRoom, ChatRole.AI, "어떤 영화를 보셨나요?");

        return AiChatRoomRes.NewAiChatRoom.builder()
                .aiChatRoomId(AiChatRoom.getId())
                .firstResponse("안녕하세요! 저는 필리, 여러분과 함께\n" +
                        "영화의 숨겨진 의미를 찾아가는 친구에요.\n" +
                        "궁금한 장면이나 감독의 의도가 있다면 편하게 물어보세요!")
                .secondResponse("어떤 영화를 보셨나요?")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public AiChatRoomRes.AiChatRoomHistory getAiChatRoomHistory(User user, Long aiChatRoomId, int page, int size) {
        // 페이지 요청에 맞는 Pageable 생성 (내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // repository에서 페이징 조회
        Page<AiChat> chatPage = aiChatRepository.findByAiChatRoomId(aiChatRoomId, pageable);

        // DTO 변환
        List<AiChatRoomRes.AiChatHistory> historyList =
                chatPage.getContent().stream()
                        .map(chat -> AiChatRoomRes.AiChatHistory.builder()
                                .aiChatId(chat.getId())
                                .ChatRole(chat.getChatRole())
                                .contents(chat.getContents())
                                .createdAt(String.valueOf(chat.getCreatedAt()))
                                .build())
                        .toList();

        return AiChatRoomRes.AiChatRoomHistory.builder()
                .page(page)
                .size(size)
                .historyList(historyList)
                .build();
    }

    @Override
    public AiChatRoomRes.RecentAiChatRoom getRecentAiChatRoom(User user) {
        Optional<AiChatRoom> recentRoomOpt = aiChatRoomRepository
                .findTopByUserOrderByCreatedAtDesc(user);

        if (recentRoomOpt.isEmpty()) {
            return AiChatRoomRes.RecentAiChatRoom.builder()
                    .aiChatRoomId(0L)
                    .build();
        }

        AiChatRoom recentAiChatRoom = recentRoomOpt.get();

        return AiChatRoomRes.RecentAiChatRoom.builder()
                .aiChatRoomId(recentAiChatRoom.getId())
                .build();
    }


}
