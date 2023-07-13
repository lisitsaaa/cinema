package com.example.cinema.service;

import com.example.cinema.entity.user.User;
import com.example.cinema.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void init(){
        user = User.builder()
                .username("Tanya")
                .email("tanyashka@gamil.com")
                .age(16)
                .password("1111")
                .build();
    }

    @Test
    @DisplayName("JUnit test for save method")
    void save() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User save = userService.save(user);

        assertNotNull(save);
    }

    @Test
    @DisplayName("JUnit test for remove method")
    void remove() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        userService.remove(user.getId());
    }

    @Test
    @DisplayName("JUnit test for findById method")
    void findById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User userById = userService.findById(user.getId());

        assertNotNull(userById);
    }

    @Test
    @DisplayName("JUnit test for findByName method")
    void findByUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User userByUsername = userService.findByUsername(user.getUsername());

        assertNotNull(userByUsername);
    }
}