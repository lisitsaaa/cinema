package com.example.cinema.controller;

import com.example.cinema.configuration.jwt.JWTTokenProvider;
import com.example.cinema.dto.UserAuthorizationDto;
import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.Role;
import com.example.cinema.entity.user.User;
import com.example.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

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
            return badRequest().build();
        }
        return ok(userService.save(buildRegistrationUser(regDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorization(@RequestBody @Valid UserAuthorizationDto authDto,
                                              BindingResult bindingResult){
        if (getValidationResult(bindingResult)) {
            User login = userService.login(buildAuthorizationUser(authDto));
            String token = jwtTokenProvider.generateToken(login.getUsername(), login.getRoles());
            return ok(token);
        }
        return badRequest().body("Invalid information");
    }

    private User buildAuthorizationUser(UserAuthorizationDto authDto){
        return User.builder()
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .build();
    }

    private User buildRegistrationUser(UserRegistrationDto regDto){
        return User.builder()
                .username(regDto.getUsername())
                .password(regDto.getPassword())
                .email(regDto.getEmail())
                .age(regDto.getAge())
                .roles(Set.of(Role.USER))
                .build();
    }
}
