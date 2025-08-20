package com.example.sw_be.domain.user.service;

import com.example.sw_be.domain.aiChat.repository.AiChatRepository;
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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AiChatRoomRepository aiChatRoomRepository;

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
        aiChatRoomRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
