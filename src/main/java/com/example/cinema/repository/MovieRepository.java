package com.example.cinema.repository;

import com.example.cinema.entity.cinema.movie.AgeLimit;
import com.example.cinema.entity.cinema.movie.Genre;
import com.example.cinema.entity.cinema.movie.Movie;
import com.example.cinema.entity.cinema.movie.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);

//    @Modifying
//    @Query("update Movie m set m.name=:name," +
//            " m.description=: description," +
//            " m.image=: image, " +
//            "m.ageLimit=: ageLimit, " +
//            "m.duration=: duration, " +
//            "m.genres=: genres, " +
//            "m.releaseYear=: releaseYear, " +
//            "m.type=: type " +
//            "where m.id=: id")
//    void update(long id, String name,
//                String image, int duration,
//                int releaseYear, String description,
//                AgeLimit ageLimit, List<Genre> genre, MovieType movieType);
}
