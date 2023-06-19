package com.example.cinema.controller;

import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.Telephone;
import com.example.cinema.entity.user.User;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.save(buildRegistrationUser(userDto)));
    }

    private boolean getValidationResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String > errors = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            System.out.println(errors);
            return false;
        }
        return true;
    }

    private User buildRegistrationUser(UserRegistrationDto userDto){
        return User.builder()
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
    }
}
