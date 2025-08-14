package com.example.sw_be.domain.user.dto.response;

import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.global.auth.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserResponse {
    private boolean isRegistered;
    private Long id ;
    private String nickname;
    private String email;
    private String profileUrl;

    public UserResponse(User user, boolean isRegistered) {
        this.isRegistered= isRegistered;
        this.id= user.getUserid();
        this.nickname= user.getNickName();
        this.email= user.getEmail();
        this.profileUrl= user.getProfileImg();
    }

    public UserResponse(User user) {
        this.isRegistered= true;
        this.id= user.getUserid();
        this.nickname= user.getNickName();
        this.email= user.getEmail();
        this.profileUrl= user.getProfileImg();
    }
}
