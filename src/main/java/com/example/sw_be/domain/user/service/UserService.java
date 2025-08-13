package com.example.sw_be.domain.user.service;

import com.example.sw_be.domain.user.dto.request.UserUpdateRequest;
import com.example.sw_be.domain.user.dto.response.UserResponse;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.domain.user.repository.UserRepository;
import com.example.sw_be.global.exception.NicknameDuplicateException;
import com.example.sw_be.global.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserResponse changeNickname(UserUpdateRequest userUpdateRequest, User user) {
        if(user== null) throw new UnauthenticatedException();

        String newNick= userUpdateRequest.getNickname();
        if (userRepository.existsByNickname(newNick))
            throw new NicknameDuplicateException(newNick);

        user.chageNickName(user.getNickName());
        userRepository.save(user);
        return new UserResponse(user);
    }

    public void deleteUser(User user) {
        if(user== null) throw new UnauthenticatedException();

        userRepository.delete(user);
    }
}
