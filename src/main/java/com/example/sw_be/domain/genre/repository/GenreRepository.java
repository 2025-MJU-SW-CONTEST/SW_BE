package com.example.sw_be.domain.genre.repository;

import com.example.sw_be.domain.genre.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
