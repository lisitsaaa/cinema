package com.example.cinema.dto;

import com.example.cinema.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class UserDto {
    @NotNull @NotEmpty @NotBlank
    private String username;

    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = "^[\\w-]{2,16}@([\\w-]{2,5}\\.)+[\\w-]{2,4}$")
    private String email;

    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = "^(?=\\d*)(?=[a-z]*)(?=[A-Z]*)(?=[\\W]*).{2,16}$")
    private String password;

    @NotNull @Min(value = 0)
    private int age;

    private Set<Role> roles;
}
