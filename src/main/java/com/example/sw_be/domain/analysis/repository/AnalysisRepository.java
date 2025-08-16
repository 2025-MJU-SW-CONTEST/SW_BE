package com.example.sw_be.domain.analysis.repository;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByUser(User user);
    Page<Analysis> findByMovieId(Long movieId, Pageable pageable);
    boolean existsByMovieIdAndUser(Long movieId, User user);
}
