package com.example.sw_be.domain.review.controller;

import com.example.sw_be.domain.review.dto.request.ReviewCreateRequest;
import com.example.sw_be.domain.review.dto.request.ReviewUpdateRequest;
import com.example.sw_be.domain.review.dto.response.ReviewResponse;
import com.example.sw_be.domain.review.service.ReviewService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Tag(name = "Review", description = "감상평 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(summary = "감상평 생성")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest,
                                                       @Parameter(hidden = true) User user){
        return ResponseEntity.ok(reviewService.createReview(reviewCreateRequest,user));
    }


    @GetMapping("/{date}")
    @Operation(summary = "감상평 조회")
    public ResponseEntity<List<ReviewResponse>> getReview(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(example = "2025-08-13", description = "조회할 날짜") LocalDate date,
            @Parameter(hidden = true) User user){
        return ResponseEntity.ok(reviewService.getReview(date, user));
    }

    @PutMapping("/update")
    @Operation(summary = "감상평 수정")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                                           @Parameter(hidden = true) User user){
        return ResponseEntity.ok(reviewService.updateReview(reviewUpdateRequest,user));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "감상평 삭제")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, @Parameter(hidden = true) User user){
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dates/{year}/{month}")
    @Operation(summary = "월별 리뷰 작성일 조회")
    public ResponseEntity<List<LocalDate>> getReviewDatesInMonth(
            @PathVariable int year,
            @PathVariable int month,
            @Parameter(hidden = true) User user) {
        return ResponseEntity.ok(reviewService.getReviewDatesInMonth(user, year, month));
    }

}
