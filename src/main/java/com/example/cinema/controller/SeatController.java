package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.service.HallService;
import com.example.cinema.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private HallService hallService;

    @PostMapping("/{hall_id}")
    public ResponseEntity<Seat> save(@PathVariable long hall_id,
                                     @RequestBody @Valid SeatDto dto,
                                     BindingResult bindingResult){
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        return ok(seatService.save(getSeat(dto, hall_id)));
    }

    private Seat getSeat(SeatDto dto, long hall_id){
        return Seat.builder()
                .row(dto.getRow())
                .seat(dto.getSeat())
                .seatType(dto.getSeatType())
                .seatStatus(dto.getSeatStatus())
                .hall(hallService.findById(hall_id))
                .build();
    }
}
