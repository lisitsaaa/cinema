package com.example.cinema.controller;

import com.example.cinema.controller.util.Validator;
import com.example.cinema.dto.SeatCreatingDto;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @PostMapping
    public ResponseEntity<Seat> save(@RequestBody @Valid SeatCreatingDto dto,
                                     BindingResult bindingResult){
        if (!getValidationResult(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(seatService.save(getSeat(dto)));
    }

    private Seat getSeat(SeatCreatingDto dto){
        return Seat.builder()
                .row(dto.getRow())
                .seat(dto.getSeat())
                .seatType(dto.getSeatType())
                .status(dto.getStatus())
                .hall(dto.getHall())
                .build();
    }
}
