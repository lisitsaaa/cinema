package com.example.cinema.controller;

import com.example.cinema.dto.UserAuthorizationDto;
import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.Telephone;
import com.example.cinema.entity.user.User;
import com.example.cinema.security.JWTTokenProvider;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody @Valid UserRegistrationDto regDto,
                                             BindingResult bindingResult){
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.save(buildRegistrationUser(regDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorization(@RequestBody @Valid UserAuthorizationDto authDto,
                                              BindingResult bindingResult){
        if (getValidationResult(bindingResult)) {
            User login = userService.login(buildAuthorizationUser(authDto));
            String token = jwtTokenProvider.generateToken(login.getUsername(), login.getRoles());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid information");
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

    private User buildAuthorizationUser(UserAuthorizationDto authDto){
        return User.builder()
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .build();
    }

    private User buildRegistrationUser(UserRegistrationDto regDto){
        return User.builder()
                .firstName(regDto.getFirstName())
                .lastName(regDto.getLastName())
                .username(regDto.getUsername())
                .password(regDto.getPassword())
                .email(regDto.getEmail())
                .age(regDto.getAge())
                .telephone(Telephone.builder()
                        .code(regDto.getCode())
                        .number(regDto.getNumber())
                        .build())
                .build();
    }
}
