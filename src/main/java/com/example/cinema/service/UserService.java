package com.example.cinema.service;

import com.example.cinema.entity.user.Role;
import com.example.cinema.entity.user.User;
import com.example.cinema.exception.ExistsException;
import com.example.cinema.exception.InvalidDataException;
import com.example.cinema.exception.NotFoundException;
import com.example.cinema.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
@Transactional
public class UserService implements UserDetailsService, AbstractService<User> {
    @Autowired
    private UserRepository userRepository;

    private final static String ADMIN_PASSWORD = "ADMIN";

    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(User user) {
        logger.info("saving is started");

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            logger.info("user already existed");
            throw new ExistsException(String.format("User with username - %s already existed", user.getUsername()));
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));

        if (user.getRoles().equals(Set.of(Role.ADMIN))) {
            if (!passwordEncoder().matches(ADMIN_PASSWORD, user.getPassword())) {
                logger.info("password isn't correct");
                throw new InvalidDataException("password isn't correct");
            }
        }

        logger.info("user was successfully saved");
        return userRepository.save(user);
    }

    @Override
    public void remove(long id) {
        logger.info("removing is started");
        userRepository.delete(findById(id));
        logger.info("removing was successfully finished");
    }

    @Override
    public User findById(long id) {
        logger.info("searching by id is started");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("searching by id was successfully finished");
            return user.get();
        }
        logger.info("user not found");
        throw new NotFoundException(String.format("User with id - %s not found", id));
    }

    public void updatePassword(User user) {
        logger.info("password updating is started");
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.updatePassword(user.getId(), user.getPassword());
        logger.info("password updating was successfully finished");
    }

    public void update(User user) {
        logger.info("personal info updating is started");
        userRepository.updatePersonalInfo(user.getId(), user.getUsername(), user.getEmail());
        logger.info("personal info updating was successfully finished");
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        logger.info("searching by username is started");
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            logger.info("searching by username was successfully finished");
            return byUsername.get();
        }
        logger.info("user not found");
        throw new NotFoundException(String.format("User with username - %s not found", username));
    }

    public User login(User user) {
        User byUsername = findByUsername(user.getUsername());
        if (passwordEncoder().matches(user.getPassword(), byUsername.getPassword())) {
            return byUsername;
        }
        logger.info("Password isn't correct");
        throw new InvalidDataException("Password isn't correct");
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
