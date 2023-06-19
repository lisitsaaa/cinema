package com.example.cinema.dto;

import com.example.cinema.entity.user.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class UserRegistrationDto {
    @NotNull @NotEmpty @NotBlank
    private String firstName;

    @NotNull @NotEmpty @NotBlank
    private String lastName;

    @NotNull @NotEmpty @NotBlank
    private String username;

    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = "^[\\w-]{2,16}@([\\w-]{2,5}\\.)+[\\w-]{2,4}$")
    private String email;

    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = "^(?=\\d*)(?=[a-z]*)(?=[A-Z]*)(?=[\\W]*).{2,16}$")
    private String password;

    @NotNull @NotEmpty @NotBlank
    @Min(value = 0)
    private int age;

    @NotNull @NotEmpty @NotBlank
//    @Pattern(regexp = ) find validation for phone numbers
    private String code;
    @NotNull @NotEmpty @NotBlank
    private String number;
}