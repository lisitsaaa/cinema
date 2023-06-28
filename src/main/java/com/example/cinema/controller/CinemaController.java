package com.example.cinema.controller;

import com.example.cinema.dto.CinemaCreatingDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.*;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> create(@RequestBody @Valid CinemaCreatingDto cinemaCreatingDto,
                                         BindingResult bindingResult){
        if(!getValidationResult(bindingResult)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cinemaService.save(buildCinema(cinemaCreatingDto)));
    }

    private Cinema buildCinema(CinemaCreatingDto dto){
        return Cinema.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .halls(dto.getHalls())
                .build();
    }
}
