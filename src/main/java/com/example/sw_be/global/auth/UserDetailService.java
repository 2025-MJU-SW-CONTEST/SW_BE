package com.example.sw_be.global.auth;

import com.example.sw_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (userRepository.existsByEmail(username)) {
            log.info("존재함");
            return new UserDetail(userRepository.findByEmail(username).get());
        }
        throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
    }
}