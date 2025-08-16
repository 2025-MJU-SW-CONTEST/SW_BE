package com.example.sw_be.domain.user.controller;

import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.service.AnalysisService;
import com.example.sw_be.domain.user.dto.request.UserUpdateRequest;
import com.example.sw_be.domain.user.dto.response.UserResponse;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
@Tag(name = "My", description = "마이페이지 API")
public class UserController {

    private final UserService userService;
    private final AnalysisService analysisService;

    @PutMapping("/nickname")
    @Operation(summary = "닉네임 수정")
    public ResponseEntity<UserResponse> changeNickname(@RequestBody UserUpdateRequest userUpdateRequest, @Parameter(hidden = true) User user){
        return ResponseEntity.ok(userService.changeNickname(userUpdateRequest, user));
    }

    @GetMapping("")
    @Operation(summary = "정보조회", description = "header에 토큰 넣어서 요청")
    public ResponseEntity<UserResponse> getUserInfo(@Parameter(hidden = true) User user){
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/analysis")
    @Operation(summary = "해석 리스트 조회", description = "header에 토큰 넣어서 요청")
    public ResponseEntity<List<AnalysisResponse>> getUserAnalysis(@Parameter(hidden = true) User user){
        return ResponseEntity.ok(analysisService.getUserAnalysis(user));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원탈퇴", description = "header에 토큰 넣어서 요청")
    public ResponseEntity<Void> deleteUser(@Parameter(hidden = true) User user){
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }


}
