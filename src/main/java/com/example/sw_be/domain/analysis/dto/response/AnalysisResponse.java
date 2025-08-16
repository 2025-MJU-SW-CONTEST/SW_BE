package com.example.sw_be.domain.analysis.dto.response;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.analysisHashtag.dto.AnalysisHashtagResponse;
import com.example.sw_be.domain.analysisHashtag.entity.AnalysisHashtag;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AnalysisResponse {
    private long analysis_id;
    private String content;
    private List<String> hashtags;
    private LocalDateTime createdAt;

    public AnalysisResponse(Analysis analysis){
        this.analysis_id= analysis.getId();
        this.content= analysis.getContent();
        this.createdAt= analysis.getCreatedAt();
        this.hashtags = analysis.getHashtags()
                .stream()
                .map(AnalysisHashtag::getHashtag).toList();
    }

}
