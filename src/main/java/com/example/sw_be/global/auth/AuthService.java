package com.example.sw_be.global.auth;

import com.example.sw_be.domain.user.dto.request.SignupRequest;
import com.example.sw_be.domain.user.dto.response.UserResponse;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserResponse oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        String accessToken = kakaoUtil.getAccessTokenFromKakao(accessCode);
        KakaoUserInfoResponseDto userInfo = kakaoUtil.getUserInfo(accessToken);

        String email = userInfo.getKakaoAccount().getEmail();
        Optional<User> optional= userRepository.findByEmail(email);

        if(optional.isPresent()) {
            User user= optional.get();
            String token = jwtUtil.createJwt(user, 60 * 60 * 1000L);
            httpServletResponse.setHeader("Authorization", token);
            return new UserResponse(user,true);
        }

        String tmptoken= jwtUtil.createPreRegisterToken(email,10 * 60 * 1000L);
        httpServletResponse.setHeader("Authorization", tmptoken);
        return new UserResponse(User.builder().profileImg(userInfo.getKakaoAccount().getProfile().getProfileImageUrl()).build(), false);

    }


    public UserResponse signup(SignupRequest signupRequest, String token, HttpServletResponse httpServletResponse) {
        String email= jwtUtil.getUsername(token);

        if(userRepository.existsByEmail(email)) throw new RuntimeException("이미 존재합니다.");
        User newUser = User.builder().nickName(signupRequest.getNickname())
                .email(jwtUtil.getUsername(token)) // jwtUtil에서 임시토큰보고 검증해서 이메일 가져옴
                .role("ROLE_USER")
                .profileImg(signupRequest.getProfileUrl())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()) // 인코딩한 랜덤 비번 줌
                ).build();
        userRepository.save(newUser);

        String newToken = jwtUtil.createJwt(newUser, 60 * 60 * 1000L);
        httpServletResponse.setHeader("Authorization", newToken);
        return new UserResponse(newUser,true);
    }
}
