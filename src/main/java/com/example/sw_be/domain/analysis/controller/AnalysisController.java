package com.example.sw_be.domain.analysis.controller;

import com.example.sw_be.domain.analysis.dto.request.AnalysisCreateRequest;
import com.example.sw_be.domain.analysis.dto.request.AnalysisUpdateRequest;
import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.service.AnalysisService;
import com.example.sw_be.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
@Tag(name = "Analysis", description = "해석 관련 API")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create")
    public ResponseEntity<AnalysisResponse> createAnalysis(@RequestBody AnalysisCreateRequest analysisCreateRequest,
                                                           @Parameter(hidden = true) User user){
        return ResponseEntity.ok(analysisService.createAnalysis(analysisCreateRequest,user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponse> getAnalysis(@PathVariable Long id){
        return ResponseEntity.ok(analysisService.getAnalysis(id));
    }

    @PutMapping("/update")
    public ResponseEntity<AnalysisResponse> updateAnalysis(@RequestBody AnalysisUpdateRequest analysisUpdateRequest,
                                                           @Parameter(hidden = true) User user){
        return ResponseEntity.ok(analysisService.updateAnalysis(analysisUpdateRequest,user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id, @Parameter(hidden = true) User user){
        analysisService.deleteAnalysis(id, user);
        return ResponseEntity.noContent().build();
    }
}
