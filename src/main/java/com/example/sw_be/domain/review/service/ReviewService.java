package com.example.sw_be.domain.review.service;

import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.service.MovieService;
import com.example.sw_be.domain.review.dto.request.ReviewCreateRequest;
import com.example.sw_be.domain.review.dto.request.ReviewUpdateRequest;
import com.example.sw_be.domain.review.dto.response.ReviewResponse;
import com.example.sw_be.domain.review.entity.Review;
import com.example.sw_be.domain.review.repository.ReviewRepository;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.global.exception.AnalysisAccessDeniedException;
import com.example.sw_be.global.exception.AnalysisNotFoundException;
import com.example.sw_be.global.exception.ReviewNotFoundException;
import com.example.sw_be.global.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.renderable.RenderableImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MovieService movieService;
    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewCreateRequest reviewCreateRequest, User user) {
        if(user== null) throw new UnauthenticatedException();

//        Movie movie= movieService.findById(reviewCreateRequest.getMovie_id());

        Review review= Review.builder()
                .title(reviewCreateRequest.getTitle())
                .content(reviewCreateRequest.getContent())
//                .rating(reviewCreateRequest.getRating())
//                .movie(movie)
                .user(user)
                .createdAt(reviewCreateRequest.getDate().atStartOfDay()).build();
        return new ReviewResponse(review);
    }

    public ReviewResponse updateReview(ReviewUpdateRequest reviewUpdateRequest, User user) {
        if(user== null) throw new UnauthenticatedException();

        Long id= reviewUpdateRequest.getId();
        Review review= reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        if (!review.getUser().getUserid().equals(user.getUserid())) throw new AnalysisAccessDeniedException(id);

        review.update(review.getTitle(), review.getContent());
        return new ReviewResponse(review);
    }


    public void deleteReview(Long id, User user) {

        if(user== null) throw new UnauthenticatedException();

        Review review= reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        if (!review.getUser().getUserid().equals(user.getUserid())) throw new AnalysisAccessDeniedException(id);

    }

    public List<ReviewResponse> getReview(LocalDate date, User user) {
        if (user == null) throw new UnauthenticatedException();

        List<Review> reviews = reviewRepository.findByUserAndDate(user, date.atStartOfDay()
                ,date.plusDays(1).atStartOfDay());

        List<ReviewResponse> responses= new ArrayList<>();

//        if (reviews.isEmpty()) throw new ReviewNotFoundException(date);

        for(Review review: reviews) responses.add(new ReviewResponse(review));
        return responses;
    }

    public List<LocalDate> getReviewDatesInMonth(User user, int year, int month) {
        if (user == null) throw new UnauthenticatedException();
        return reviewRepository.findReviewDatesInMonth(user, year, month)
                .stream()
                .map(java.sql.Date::toLocalDate)
                .toList();
    }

}

