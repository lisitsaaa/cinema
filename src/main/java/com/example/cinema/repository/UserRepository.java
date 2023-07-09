package com.example.cinema.repository;

import com.example.cinema.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.username=: username, " +
            "u.email=: email, " +
            "u.age=: age, " +
            "u.password=: password " +
            "where u.id=: id")
    void update(long id,
                String email,
                String password,
                String username,
                int age);
}
