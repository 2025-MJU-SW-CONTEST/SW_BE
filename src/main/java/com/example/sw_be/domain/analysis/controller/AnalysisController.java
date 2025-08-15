package com.example.sw_be.domain.analysis.controller;

import com.example.sw_be.domain.analysis.dto.request.AnalysisCreateRequest;
import com.example.sw_be.domain.analysis.dto.request.AnalysisUpdateRequest;
import com.example.sw_be.domain.analysis.dto.response.AnalysisResponse;
import com.example.sw_be.domain.analysis.service.AnalysisService;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.user.entity.User;
import com.example.sw_be.global.common.PageResponse;
import com.example.sw_be.global.common.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
@Tag(name = "Analysis", description = "해석 관련 API")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create")
    @Operation(summary = "해석 생성")
    public ResponseEntity<AnalysisResponse> createAnalysis(@RequestBody AnalysisCreateRequest analysisCreateRequest,
                                                           @Parameter(hidden = true) User user){
        return ResponseEntity.ok(analysisService.createAnalysis(analysisCreateRequest,user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "개별 해석 조회")
    public ResponseEntity<AnalysisResponse> getAnalysis(@PathVariable Long id){
        return ResponseEntity.ok(analysisService.getAnalysis(id));
    }

    @PutMapping("/update")
    @Operation(summary = "해석 수정")
    public ResponseEntity<AnalysisResponse> updateAnalysis(@RequestBody AnalysisUpdateRequest analysisUpdateRequest,
                                                           @Parameter(hidden = true) User user){
        return ResponseEntity.ok(analysisService.updateAnalysis(analysisUpdateRequest,user));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "헤석 삭제")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id, @Parameter(hidden = true) User user){
        analysisService.deleteAnalysis(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movie/{id}")
    @Operation(summary = "영화별 해석 리스트 조회", description = "페이지 단위로 영화별 해석 리스트 조회")
    public ResponseEntity<PageResponse<AnalysisResponse>> getMovieList(@PathVariable Long id,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Page<AnalysisResponse> analysisList = analysisService.getAnalysisList(id, PageUtil.defaultPage(page, size));
        return ResponseEntity.ok(new PageResponse<>(analysisList));
    }
}
