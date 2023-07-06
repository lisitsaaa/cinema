package com.example.cinema.controller;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.*;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> create(@RequestBody @Valid CinemaDto dto,
                                         BindingResult bindingResult){
        if(!getValidationResult(bindingResult)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cinemaService.save(buildCinema(dto)));
    }

    private Cinema buildCinema(CinemaDto dto){
        return Cinema.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> findById(@PathVariable long id){
        return ResponseEntity.ok(cinemaService.findById(id));
    }
}
