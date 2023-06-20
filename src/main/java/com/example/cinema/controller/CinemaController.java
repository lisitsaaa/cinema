package com.example.cinema.controller;

import com.example.cinema.dto.CinemaCreatingDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    private boolean getValidationResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                errors.put(fieldError.getCode(), fieldError.getDefaultMessage());
            }
            System.out.println(errors);
            return false;
        }
        return true;
    }

    private Cinema buildCinema(CinemaCreatingDto dto){
        return Cinema.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .halls(dto.getHalls())
                .build();
    }
}
