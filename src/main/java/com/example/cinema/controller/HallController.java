package com.example.cinema.controller;

import com.example.cinema.dto.HallDto;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.service.CinemaService;
import com.example.cinema.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;

@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    private HallService hallService;

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/{cinema_id}")
    public ResponseEntity<Hall> create(@PathVariable long cinema_id,
                                       @RequestBody @Valid HallDto dto,
                                       BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(hallService.save(buildHall(dto, cinema_id)));
    }

    private Hall buildHall(HallDto dto, long cinema_id) {
        return Hall.builder()
                .name(dto.getName())
                .seats(dto.getSeats())
                .cinema(cinemaService.findById(cinema_id))
                .build();
    }

    @GetMapping("/id")
    public ResponseEntity<Hall> findById(@PathVariable long id) {
        return ResponseEntity.ok(hallService.findById(id));
    }
}
