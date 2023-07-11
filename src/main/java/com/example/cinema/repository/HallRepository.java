package com.example.cinema.repository;

import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.entity.cinema.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findByName(String name);

    List<Hall> findByCinema(Cinema cinema);

    @Modifying
    @Query("update Hall h set h.name=:name where h.id=:id")
    void update(@Param("id") long id, @Param("name") String name);

}
