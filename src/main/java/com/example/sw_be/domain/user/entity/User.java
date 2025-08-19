package com.example.sw_be.domain.user.entity;


import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder.Default
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Analysis> analyses = new ArrayList<>();

    public void changeNickName(String nickName){
        this.nickName= nickName;
    }

}
