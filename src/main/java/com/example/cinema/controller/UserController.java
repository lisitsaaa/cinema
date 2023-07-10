package com.example.cinema.controller;

import com.example.cinema.configuration.jwt.JWTTokenProvider;
import com.example.cinema.dto.UserAuthorizationDto;
import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.Role;
import com.example.cinema.entity.user.User;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.UserAuthorizationMapper.AUTH_INSTANCE;
import static com.example.cinema.mapper.UserRegistrationMapper.REG_INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody @Valid UserRegistrationDto regDto,
                                             BindingResult bindingResult){
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        regDto.setRoles(Set.of(Role.USER));
        return ok(userService.save(REG_INSTANCE.dtoToUser(regDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorization(@RequestBody @Valid UserAuthorizationDto authDto,
                                                BindingResult bindingResult){
        if (getValidationResult(bindingResult)) {
            User login = userService.login(AUTH_INSTANCE.dtoToUser(authDto));
            String token = jwtTokenProvider.generateToken(login.getUsername(), login.getRoles());
            return ok(token);
        }
        return badRequest().body("Invalid information");
    }

}
