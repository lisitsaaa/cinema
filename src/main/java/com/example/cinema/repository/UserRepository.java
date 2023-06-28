package com.example.cinema.repository;

import com.example.cinema.entity.user.Telephone;
import com.example.cinema.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelephone(Telephone telephone);
    Optional<User> findByUsername(String username);
}
