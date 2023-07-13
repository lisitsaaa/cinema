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

import java.util.ArrayList;
import java.util.List;

import static com.example.cinema.controller.util.Validator.checkBindingResult;
import static com.example.cinema.mapper.HallMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/hall")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;
    private final CinemaService cinemaService;

    @PostMapping("/admin/{cinema_id}")
    public ResponseEntity<HallDto> create(@PathVariable long cinema_id,
                                          @RequestBody @Valid HallDto dto,
                                          BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        dto.setCinema(cinemaService.findById(cinema_id));
        return ok(INSTANCE.hallToDto(hallService.save(INSTANCE.dtoToHall(dto))));
    }

    @PostMapping("/admin/update/{id}")
    public ResponseEntity<HallDto> update(@PathVariable long id,
                                          @RequestBody @Valid Hall hall,
                                          BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        Hall hallById = hallService.findById(id);
        hallById.setName(hall.getName());
        hallService.update(hallById);
        return ok(INSTANCE.hallToDto(hallById));
    }

    @DeleteMapping("/admin/{id}")
    public void remove(@PathVariable long id) {
        hallService.remove(id);
    }

    @GetMapping("/find-by-cinema/{cinema_name}")
    public List<HallDto> getByCinema(@PathVariable String cinema_name) {
        List<HallDto> hallDtoList = new ArrayList<>();
        hallService.findAllByCinema(cinemaService.findByName(cinema_name))
                .forEach(hall -> hallDtoList.add(INSTANCE.hallToDto(hall)));
        return hallDtoList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getById(@PathVariable long id) {
        return ok(INSTANCE.hallToDto(hallService.findById(id)));
    }
}
