package com.example.cinema.entity.user;

import com.example.cinema.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter @Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private Telephone telephone;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
