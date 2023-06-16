package com.example.cinema.service;

import com.example.cinema.entity.user.User;
import com.example.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

//    public User findByUsername(String username){
//        Optional<User> byUsername = userRepository.findByUsername(username);
//        if (byUsername.isPresent()) {
//            return byUsername.get();
//        }
//        throw new RuntimeException("User not found");
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new RuntimeException("User not found!"); //exception
    }
}
