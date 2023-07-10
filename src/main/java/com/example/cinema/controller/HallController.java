package com.example.cinema.controller;

import com.example.cinema.dto.HallDto;
import com.example.cinema.entity.cinema.Hall;
import com.example.cinema.service.CinemaService;
import com.example.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.HallMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/hall")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;
    private final CinemaService cinemaService;

    @PostMapping("/{cinema_id}")
    public ResponseEntity<Hall> create(@PathVariable long cinema_id,
                                       @RequestBody @Valid HallDto dto,
                                       BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        dto.setCinema(cinemaService.findById(cinema_id));
        return ok(hallService.save(INSTANCE.dtoToHall(dto)));
    }

    @GetMapping("/id")
    public ResponseEntity<Hall> findById(@PathVariable long id) {
        return ok(hallService.findById(id));
    }
}
