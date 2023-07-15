package com.example.cinema.service;

import com.example.cinema.entity.cinema.movie.AgeLimit;
import com.example.cinema.entity.cinema.movie.Genre;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.entity.cinema.movie.MovieType;
import com.example.cinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    public void init(){
        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.ACTION);
        genres.add(Genre.FANTASY);

        movie = Movie.builder()
                .name("Spider-Man: No Way Home")
                .image("image:)")
                .duration(148)
                .releaseYear(2021)
                .description("description:)")
                .genres(genres)
                .ageLimit(AgeLimit.TWELVE_PLUS)
                .type(MovieType.THREE_D)
                .build();
    }

    @Test
    void save() {
        when(movieRepository.findByNameAndReleaseYear(movie.getName(), movie.getReleaseYear()))
                .thenReturn(empty());
        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);

        Movie savedMovie = movieService.save(movie);

        assertNotNull(savedMovie);
    }

    @Test
    void findById() {
        when(movieRepository.findById(movie.getId())).thenReturn(of(movie));

        Movie movieById = movieService.findById(movie.getId());

        assertNotNull(movieById);
    }

    @Test
    void findAll() {
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.findAll();

        assertNotNull(movies);
    }

    @Test
    void findByName() {
        when(movieRepository.findByName(movie.getName())).thenReturn(of(movie));

        Movie movieByName = movieService.findByName(movie.getName());

        assertNotNull(movieByName);
    }
}