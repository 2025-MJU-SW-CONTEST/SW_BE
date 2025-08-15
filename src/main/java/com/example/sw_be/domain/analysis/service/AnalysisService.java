package com.example.sw_be.domain.analysis.service;

import com.example.sw_be.domain.analysis.dto.request.AnalysisCreateRequest;
import com.example.sw_be.domain.analysis.dto.request.AnalysisUpdateRequest;
import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.analysis.repository.AnalysisRepository;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.service.MovieService;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.global.exception.AnalysisAccessDeniedException;
import com.example.sw_be.global.exception.AnalysisNotFoundException;
import com.example.sw_be.global.exception.UnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final MovieService movieService;

    public AnalysisResponse createAnalysis(AnalysisCreateRequest analysisCreateRequest, User user) {

        if(user== null) throw new UnauthenticatedException();

        Movie movie= movieService.findById(analysisCreateRequest.getMovie_id());
        Analysis analysis= Analysis.builder()
                .content(analysisCreateRequest.getContent())
                .movie(movie)
                .user(user)
                .createdAt(LocalDateTime.now()).build();
        return new AnalysisResponse(analysisRepository.save(analysis));
    }

    public AnalysisResponse updateAnalysis(AnalysisUpdateRequest analysisUpdateRequest, User user) {

        if(user== null) throw new UnauthenticatedException();

        Long id= analysisUpdateRequest.getAnalysis_id();
        Analysis analysis= analysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        if (!analysis.getUser().getUserid().equals(user.getUserid())) throw new AnalysisAccessDeniedException(id);

        analysis.update(analysis.getContent());
        return new AnalysisResponse(analysis);
    }


    public AnalysisResponse getAnalysis(Long id) {
        Analysis analysis= analysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        return new AnalysisResponse(analysis);
    }


    public void deleteAnalysis(Long id, User user) {

        if(user== null) throw new UnauthenticatedException();

        Analysis analysis= analysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        if (!analysis.getUser().getUserid().equals(user.getUserid())) throw new AnalysisAccessDeniedException(id);

        analysisRepository.delete(analysis);
    }

    public List<AnalysisResponse> getUserAnalysis(User user) {
        List<Analysis> analyses= analysisRepository.findByUser(user);
        List<AnalysisResponse> responses= new ArrayList<>();

        for (Analysis analysis: analyses) responses.add(new AnalysisResponse(analysis));

        return responses;
    }

    public Page<AnalysisResponse> getAnalysisList(Long movieId, Pageable pageable) {
        return analysisRepository.findByMovieId(movieId, pageable)
                .map(AnalysisResponse::new);
    }
}
