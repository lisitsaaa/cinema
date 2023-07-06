package com.example.cinema.dto;

import com.example.cinema.entity.cinema.Hall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CinemaDto {
    private String name;
    private String city;
    private List<Hall> halls;
}
