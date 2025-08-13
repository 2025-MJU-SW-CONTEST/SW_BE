package com.example.sw_be.domain.analysis.controller;

import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.service.AnalysisService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
@Tag(name = "Analysis", description = "해석 관련 API")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create")
    public ResponseEntity<AnalysisResponse> createAnalysis(@RequestBody AnalysisResponse analysisResponse, User user){
        return ResponseEntity.ok(analysisService.createAnalysis(analysisResponse,user));
    }
}
