package com.example.sw_be.domain.analysis.dto.request;

import lombok.Getter;

@Getter
public class AnalysisUpdateRequest {
    private long analysis_id;
    private String content;
}
