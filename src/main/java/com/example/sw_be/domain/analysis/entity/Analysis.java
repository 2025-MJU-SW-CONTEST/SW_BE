package com.example.sw_be.domain.analysis.entity;

import com.example.sw_be.domain.analysisHashtag.entity.AnalysisHashtag;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalysisHashtag> hashtags = new ArrayList<>();

    public void setHashtags(List<AnalysisHashtag> tags) {
        this.hashtags.clear();
        if (tags != null) {
            tags.forEach(t -> t.setAnalysis(this)); // 역참조 세팅
            this.hashtags.addAll(tags);
        }
    }

    public void update(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}
