package com.example.cinema.mapper;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.cinema.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CinemaMapper {
    CinemaMapper INSTANCE = Mappers.getMapper(CinemaMapper.class);

    Cinema dtoToUser(CinemaDto dto);
    CinemaDto cinemaToDto(Cinema entity);
}
