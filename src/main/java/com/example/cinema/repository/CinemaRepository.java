package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
}
