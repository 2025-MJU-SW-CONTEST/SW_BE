package com.example.sw_be.domain.aiChat.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTitles {
    private Result result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Long movie_id;
        private String title;
    }
}
