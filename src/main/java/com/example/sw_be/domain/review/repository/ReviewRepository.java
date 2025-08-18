package com.example.sw_be.domain.review.repository;

import com.example.sw_be.domain.review.entity.Review;
import com.example.sw_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "WHERE r.user = :user " +
            "AND r.createdAt >= :start " +
            "AND r.createdAt < :end")
    List<Review> findByUserAndDate(
            @Param("user") User user,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);


    @Query("SELECT DISTINCT DATE(r.createdAt) FROM Review r " +
            "WHERE r.user = :user " +
            "AND YEAR(r.createdAt) = :year " +
            "AND MONTH(r.createdAt) = :month")
    List<java.sql.Date> findReviewDatesInMonth(@Param("user") User user,
                                           @Param("year") int year,
                                           @Param("month") int month);
}
