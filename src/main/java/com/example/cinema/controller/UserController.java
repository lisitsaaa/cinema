package com.example.cinema.controller;

import com.example.cinema.configuration.jwt.JWTTokenProvider;
import com.example.cinema.dto.UserAuthorizationDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.user.User;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.UserAuthorizationMapper.AUTH_INSTANCE;
import static com.example.cinema.mapper.UserMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<UserDto> registration(@RequestBody @Valid UserDto regDto,
                                                BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.userToDto(userService.save(INSTANCE.dtoToUser(regDto))));
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorization(@RequestBody @Valid UserAuthorizationDto authDto,
                                                BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        User login = userService.login(AUTH_INSTANCE.dtoToUser(authDto));
        String token = jwtTokenProvider.generateToken(login.getUsername(), login.getRoles());
        return ok(token);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        userService.remove(id);
    }

    @PostMapping("/update-password")
    public ResponseEntity<UserDto> updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody @Valid User user,
                                                  BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        User byUsername = userService.findByUsername(userDetails.getUsername());
        byUsername.setPassword(user.getPassword());
        userService.updatePassword(byUsername);
        return ok(INSTANCE.userToDto(byUsername));
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updatePersonalInfo(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody @Valid User user,
                                                      BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        User byUsername = userService.findByUsername(userDetails.getUsername());

        if (!user.getUsername().isEmpty()) {
            byUsername.setUsername(user.getUsername());
        }
        if (!user.getEmail().isEmpty()) {
            byUsername.setEmail(user.getEmail());
        }
        userService.update(byUsername);
        return ok(INSTANCE.userToDto(byUsername));
    }
}
