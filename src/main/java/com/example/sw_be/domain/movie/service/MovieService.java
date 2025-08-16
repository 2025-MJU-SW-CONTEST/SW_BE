package com.example.sw_be.domain.movie.service;

import com.example.sw_be.domain.analysis.entity.Analysis;
import com.example.sw_be.domain.genre.repository.GenreRepository;
import com.example.sw_be.domain.movie.dto.MovieApiResponse;
import com.example.sw_be.domain.movie.dto.MovieDetailResponse;
import com.example.sw_be.domain.movie.dto.MovieResponse;
import com.example.sw_be.domain.movie.entity.Movie;
import com.example.sw_be.domain.movie.repository.MovieRepository;
import com.example.sw_be.domain.movieCast.entity.MovieCast;
import com.example.sw_be.domain.movieCast.service.MovieCastService;
import com.example.sw_be.domain.movieGenre.entity.MovieGenre;
import com.example.sw_be.global.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
//    private final GenreRepository genereRepository;
    private final MovieCastService castService;

    @Value("${spring.tmdb.api.token}")
    private String token;

    private final WebClient webClient= WebClient.builder()
            .baseUrl("https://api.themoviedb.org/3")
            .defaultHeader("Authorization", "Bearer "+token)
            .build();


    public Movie findByid(long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }


    public void insertInitialMovies() {
        for (int page = 1; page <= 10; page++) {
            int finalPage = page;
            MovieApiResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/discover/movie")
                            .queryParam("include_adult", false)
                            .queryParam("include_video", false)
                            .queryParam("language", "ko-KR")
                            .queryParam("page", finalPage)
                            .queryParam("sort_by", "popularity.desc")
                            .build())
                    .retrieve()
                    .bodyToMono(MovieApiResponse.class)
                    .block();

            if (response != null && response.getResults() != null) {
                for (MovieApiResponse.MovieDto dto : response.getResults()) {

                    Movie movie = Movie.builder()
                            .id(dto.getId())
                            .title(dto.getTitle())
                            .summary(dto.getOverview())
                            .rating(dto.getVote_average())
                            .thumbnailUrl(dto.getPoster_path())
                            .releaseDate(dto.getRelease_date())
                            .build();


//                    for (Long genreId : dto.getGenre_ids()) {
//                        genereRepository.findById(genreId).ifPresent(genre -> {
//                            MovieGenre mg = MovieGenre.builder()
//                                    .movie(movie)
//                                    .genre(genre)
//                                    .build();
//                            movie.getMovieGenres().add(mg);
//                        });
//                    }

                    List<MovieCast> casts =castService.saveCasts(dto.getId());
                    movie.setMovieCasts(casts);

                    movieRepository.save(movie);
                }
            }

        }
    }

    public List<MovieResponse> getMovies(Pageable pageable) {
        return movieRepository.findAllByOrderByReleaseDateDesc(pageable).stream()
                .map(MovieResponse::new)
                .toList();
    }

    public MovieDetailResponse getMovieDetail(Long id) {
        Movie movie= movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        return new MovieDetailResponse(movie);
    }

    public List<MovieResponse> searchMovies(String keyword) {
        List<Movie> movies = movieRepository.searchMovies(keyword);
        return movies.stream()
                .map(MovieResponse::new)
                .toList();
    }

}
