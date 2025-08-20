package com.example.sw_be.domain.user.service;

import com.example.sw_be.domain.aiChat.repository.AiChatRepository;
import com.example.sw_be.domain.aiChatRoom.entity.AiChatRoom;
import com.example.sw_be.domain.aiChatRoom.resporitory.AiChatRoomRepository;
import com.example.sw_be.domain.user.dto.request.UserUpdateRequest;
import com.example.sw_be.domain.user.dto.response.UserResponse;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.domain.user.repository.UserRepository;
import com.example.sw_be.global.exception.NicknameDuplicateException;
import com.example.sw_be.global.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AiChatRoomRepository aiChatRoomRepository;
    private final AiChatRepository aiChatRepository;
    public UserResponse changeNickname(UserUpdateRequest userUpdateRequest, User user) {
        if (user == null) throw new UnauthenticatedException();


        String newNick= userUpdateRequest.getNickname();
      
        if (userRepository.existsByNickName(newNick))
            throw new NicknameDuplicateException(newNick);

        user.changeNickName(newNick);
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Transactional
    public void deleteUser(User user) {
        if (user == null) throw new UnauthenticatedException();

        // 1. 유저가 가진 채팅방 목록 조회
        List<AiChatRoom> rooms = aiChatRoomRepository.findAllByUser(user);

        // 2. 각 채팅방에 속한 채팅 먼저 삭제
        for (AiChatRoom room : rooms) {
            aiChatRepository.deleteAllByAiChatRoom(room);
        }

        // 3. 채팅방 삭제
        aiChatRoomRepository.deleteAll(rooms);
        userRepository.delete(user);
    }
}
