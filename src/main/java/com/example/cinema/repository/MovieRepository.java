package com.example.cinema.repository;

import com.example.cinema.entity.cinema.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);

    Optional<Movie> findByNameAndReleaseYear(String name, int releaseYear);

    @Modifying
    @Query("update Movie m set m.name=:name," +
            " m.description=:description," +
            " m.image=:image, " +
            "m.duration=:duration, " +
            "m.releaseYear=:releaseYear " +
            "where m.id=:id")
    void update(@Param("id") long id, @Param("name") String name,
                @Param("description") String description, @Param("image") String image,
                @Param("duration") int duration, @Param("releaseYear") int releaseYear);
}
