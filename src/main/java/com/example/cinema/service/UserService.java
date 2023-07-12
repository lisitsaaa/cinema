package com.example.cinema.service;

import com.example.cinema.entity.user.User;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService, AbstractService<User> {
    @Autowired
    private UserRepository userRepository;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ExistsException(String.format("User with username - %s already existed", user.getUsername()));
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void remove(long id) {
        userRepository.delete(findById(id));
    }

    @Override
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NotFoundException(String.format("User with id - %s not found", id));
    }

    public void updatePassword(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.updatePassword(user.getId(), user.getPassword());
    }

    public void update(User user) {
        userRepository.updatePersonalInfo(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new NotFoundException(String.format("User with username - %s not found", username));
    }

    public User login(User user) {
        User byUsername = findByUsername(user.getUsername());
        if (passwordEncoder().matches(user.getPassword(), byUsername.getPassword())) {
            return byUsername;
        }
        throw new RuntimeException("Password isn't correct");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new NotFoundException(String.format("User with username - %s not found", username));
    }
}
