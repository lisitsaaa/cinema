package com.example.cinema.controller;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.cinema.Cinema;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.CinemaMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping("/admin/cinema")
    public ResponseEntity<CinemaDto> create(@RequestBody @Valid CinemaDto dto,
                                            BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ok(INSTANCE.cinemaToDto(cinemaService.save(INSTANCE.dtoToUser(dto))));
    }

    @PostMapping("/admin/cinema/update/{id}")
    public ResponseEntity<CinemaDto> update(@RequestBody @Valid Cinema cinema,
                                            BindingResult bindingResult,
                                            @PathVariable long id) {
        checkBindingResult(bindingResult);
        Cinema cinemaById = cinemaService.findById(id);
        cinemaById.setName(cinema.getName());
        cinemaService.update(cinemaById);
        return ok(INSTANCE.cinemaToDto(cinemaById));
    }

    @DeleteMapping("/admin/cinema/{id}")
    public void remove(@PathVariable long id) {
        cinemaService.remove(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> getById(@PathVariable long id) {
        return ok(INSTANCE.cinemaToDto(cinemaService.findById(id)));
    }

    @GetMapping("/find-by-city/{city}")
    public ResponseEntity<List<CinemaDto>> getAllByCity(@PathVariable String city) {
        List<CinemaDto> cinemaDtoList = new ArrayList<>();
        cinemaService.findByCity(city)
                .forEach(cinema -> cinemaDtoList.add(INSTANCE.cinemaToDto(cinema)));
        return ok(cinemaDtoList);
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<CinemaDto> getByName(@PathVariable String name) {
        return ok(INSTANCE.cinemaToDto(cinemaService.findByName(name)));
    }
}
