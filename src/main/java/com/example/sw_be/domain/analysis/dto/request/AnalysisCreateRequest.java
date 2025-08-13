package com.example.sw_be.domain.analysis.dto.request;

import lombok.Getter;

@Getter
public class AnalysisCreateRequest {
    private String content;
    private long movie_id;
}
