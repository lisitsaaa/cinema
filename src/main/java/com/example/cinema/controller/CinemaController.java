package com.example.cinema.controller;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> create(@RequestBody @Valid CinemaDto dto,
                                         BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        return ok(cinemaService.save(buildCinema(dto)));
    }

    private Cinema buildCinema(CinemaDto dto) {
        return Cinema.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> findById(@PathVariable long id) {
        return ok(cinemaService.findById(id));
    }

    @GetMapping("/find-by-city/{city}")
    public ResponseEntity<List<Cinema>> findByCity(@PathVariable String city) {
        return ok(cinemaService.findByCity(city));
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<Cinema> findByName(@PathVariable String name){
        return ok(cinemaService.findByName(name));
    }
}
