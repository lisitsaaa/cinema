package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);

    List<Cinema> findByCity(String city);

    Optional<Cinema> findByCityAndName(String city, String name);

    @Modifying
    @Query("update Cinema c set c.name=:name where c.id=:id")
    void update(@Param("id") long id, @Param("name") String name);
}
