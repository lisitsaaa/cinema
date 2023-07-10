package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.seat.Seat;
import com.example.cinema.service.HallService;
import com.example.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cinema.controller.util.Validator.getValidationResult;
import static com.example.cinema.mapper.SeatMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    private final HallService hallService;

    @PostMapping("/{hall_id}")
    public ResponseEntity<Seat> save(@PathVariable long hall_id,
                                     @RequestBody @Valid SeatDto dto,
                                     BindingResult bindingResult) {
        if (!getValidationResult(bindingResult)) {
            return badRequest().build();
        }
        dto.setHall(hallService.findById(hall_id));
        return ok(seatService.save(INSTANCE.dtoToSeat(dto)));
    }

}
