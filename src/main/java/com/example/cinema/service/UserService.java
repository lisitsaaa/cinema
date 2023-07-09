package com.example.cinema.service;

import com.example.cinema.entity.user.User;
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

    public User save(User user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("incorrect id");
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.update(user.getId(), user.getEmail(), user.getPassword(), user.getUsername(), user.getAge());
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username){
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new RuntimeException("User not found");
    }

    public User login(User user){
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
        throw new RuntimeException("User not found!"); //exception
    }
}
