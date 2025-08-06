package com.example.sw_be.global.auth;

import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료
            return;
        }

        log.info("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        User user = userRepository.findByEmail(username)
                .orElse(null);

        boolean isTmp = jwtUtil.isTmp(token);

        if (user == null) {
            if (isTmp) {
                log.info("임시 토큰: 사용자 없음 → SecurityContext 설정 안 함");
                filterChain.doFilter(request, response);
                return;
            } else {
                log.warn("JWT의 유저 정보로 DB에서 사용자를 찾을 수 없음: {}", username);
                filterChain.doFilter(request, response);
                return;
            }
        }


        UserDetail userDetails = new UserDetail(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
