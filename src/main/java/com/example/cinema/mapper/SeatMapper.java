package com.example.cinema.mapper;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.cinema.seat.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeatMapper {
    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    Seat dtoToSeat(SeatDto dto);
}
