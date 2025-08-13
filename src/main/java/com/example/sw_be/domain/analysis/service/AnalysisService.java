package com.example.sw_be.domain.analysis.service;

import com.example.sw_be.domain.analysis.dto.request.AnalysisCreateRequest;
import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.analysis.repository.AnalysisRepository;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final MovieService movieService;

    public AnalysisResponse createAnalysis(AnalysisCreateRequest analysisCreateRequest, User user) {
        Movie movie= movieService.findByid(analysisCreateRequest.getMovie_id());
        Analysis analysis= Analysis.builder().content(analysisCreateRequest.getContent())
                .movie(movie)
                .user(user)
                .createdAt(LocalDateTime.now()).build();
        return new AnalysisResponse(analysis);

    }
}
