package com.example.cinema.repository;

import com.example.cinema.entity.cinema.movie.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieRepositoryTest {
    @Autowired private MovieRepository movieRepository;

    @Test
    void MovieRepository_FindByName_ReturnMovie() {
        String name = "Spider-Man: No Way Home";
        Movie movie = movieRepository.findByName(name).get();

        assertNotNull(movie);
        assertEquals(name, movie.getName());
    }

    @Test
    void MovieRepository_FindByNameAndReleaseYear_ReturnMovie() {
        String name = "Spider-Man: No Way Home";
        int releaseYear = 2021;

        Movie movie = movieRepository.findByNameAndReleaseYear(name, releaseYear).get();

        assertNotNull(movie);
        assertEquals(name, movie.getName());
        assertEquals(releaseYear, movie.getReleaseYear());
    }
}