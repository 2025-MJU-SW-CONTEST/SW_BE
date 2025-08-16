package com.example.sw_be.domain.analysisHashtag.service;

import com.example.sw_be.domain.analysis.repository.AnalysisRepository;
import com.example.sw_be.domain.analysisHashtag.entity.AnalysisHashtag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalysisHashtagJob {

    private static final Logger log = LoggerFactory.getLogger(AnalysisHashtagJob.class);
    private final AnalysisRepository analysisRepository;
    private final HashtagAIService hashtagAIService;

    @Async("taskExecutor")
    @Transactional
    public void generateForAnalysis(Long analysisId, String content) {
        var tags = hashtagAIService.generateHashTags(content);
        var analysis = analysisRepository.findById(analysisId).orElseThrow();

        var entities = tags.stream()
                .map(t -> AnalysisHashtag.builder()
                        .hashtag(t)
                        .analysis(analysis)
                        .build())
                .toList();

        analysis.setHashtags(entities);
    }
}
