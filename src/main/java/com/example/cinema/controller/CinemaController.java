package com.example.cinema.controller;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.CinemaMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> create(@RequestBody @Valid CinemaDto dto,
                                         BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        return ok(cinemaService.save(INSTANCE.dtoToUser(dto)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Cinema> update(@RequestBody @Valid Cinema cinema,
                                         BindingResult bindingResult,
                                         @PathVariable long id) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        Cinema cinemaById = cinemaService.findById(id);
        cinemaById.setName(cinema.getName());
        cinemaService.update(cinemaById);
        return ok(cinemaById);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        cinemaService.remove(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getById(@PathVariable long id) {
        return ok(cinemaService.findById(id));
    }

    @GetMapping("/find-by-city/{city}")
    public ResponseEntity<List<Cinema>> getByCity(@PathVariable String city) {
        return ok(cinemaService.findByCity(city));
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<Cinema> getByName(@PathVariable String name) {
        return ok(cinemaService.findByName(name));
    }
}
