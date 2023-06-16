package com.example.cinema.entity.user;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class User extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;

    @OneToOne
    private Telephone telephone;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}