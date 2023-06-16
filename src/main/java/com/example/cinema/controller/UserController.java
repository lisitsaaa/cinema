package com.example.cinema.controller;

import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.Telephone;
import com.example.cinema.entity.user.User;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody @Valid UserRegistrationDto userDto,
                                             BindingResult bindingResult){
        Map<String, String> errors = checkErrors(bindingResult);
        if (errors.isEmpty()) {
            User user = User.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .email(userDto.getEmail())
                    .age(userDto.getAge())
                    .telephone(Telephone.builder()
                            .code(userDto.getCode())
                            .number(userDto.getNumber())
                            .build())
                    .build();
            return ResponseEntity.ok(userService.save(user));
        }
           return ResponseEntity.badRequest().build();
    }


    private Map<String, String> checkErrors(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()){
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return errors;
    }
}
