package com.example.sw_be.global.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;
    private static final int CHAT_SIZE = 30;

    public static Pageable defaultPage(Integer page, Integer size, String sort) {
        int p = page == null ? DEFAULT_PAGE : Math.max(0, page);
        int s = size == null ? DEFAULT_SIZE : Math.max(1, Math.min(size, MAX_SIZE));
        return PageRequest.of(p, s, Sort.by(Sort.Order.desc(sort)));
    }

    public static Pageable noSortPage(Integer page, Integer size) {
        int p = page == null ? DEFAULT_PAGE : Math.max(0, page);
        int s = size == null ? DEFAULT_SIZE : Math.max(1, Math.min(size, MAX_SIZE));
        return PageRequest.of(p, s);
    }

    public static Pageable chatPage() {
        return PageRequest.of(0, CHAT_SIZE, Sort.by("timestamp").ascending());
    }
}
