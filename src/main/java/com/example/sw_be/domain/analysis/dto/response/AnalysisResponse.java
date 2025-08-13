package com.example.sw_be.domain.analysis.dto.response;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.analysis.repository.AnalysisRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnalysisResponse {
    private long analysis_id;
    private String content;
    private LocalDateTime createdAt;

    public AnalysisResponse(Analysis analysis){
        this.analysis_id= analysis.getId();
        this.content= analysis.getContent();
        this.createdAt= analysis.getCreatedAt();
    }

}
