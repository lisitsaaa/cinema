package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.entity.cinema.MovieSession;
import com.example.cinema.entity.cinema.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    List<MovieSession> findByDate(LocalDate date);

    List<MovieSession> findByCinema(Cinema cinema);

    List<MovieSession> findByMovie(Movie movie);

    @Modifying
    @Query("update MovieSession ms set ms.date=:date, " +
            "ms.startedTime=:startedTime, " +
            "ms.price=:price, " +
            "ms.hall=:hall " +
            "where ms.id=:id")
    void update(@Param("id") long id, @Param("date") LocalDate date,
                @Param("startedTime") LocalTime startedTime, @Param("price") double price,
                @Param("hall") Hall hall);
}
