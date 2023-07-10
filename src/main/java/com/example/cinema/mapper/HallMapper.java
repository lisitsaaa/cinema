package com.example.cinema.mapper;

import com.example.cinema.dto.HallDto;
import com.example.cinema.entity.cinema.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HallMapper {
    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    Hall dtoToHall(HallDto dto);
}
