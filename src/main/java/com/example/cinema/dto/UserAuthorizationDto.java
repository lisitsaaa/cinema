package com.example.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationDto {
    @NotNull @NotEmpty @NotBlank
    private String username;

    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = "^(?=\\d*)(?=[a-z]*)(?=[A-Z]*)(?=[\\W]*).{2,16}$")
    private String password;
}
