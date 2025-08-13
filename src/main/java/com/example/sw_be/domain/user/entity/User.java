package com.example.sw_be.domain.user.entity;


import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.review.entity.Review;
import com.example.sw_be.domain.userChatRoom.entity.UserChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String nickName;
    private String email;
    private String profileImg;
    private String role;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Analysis analysis;

    public void chageNickName (String nickName){
        this.nickName= nickName;
    }

}
