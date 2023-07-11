package com.example.cinema.mapper;

import com.example.cinema.dto.MovieSessionDto;
import com.example.cinema.entity.cinema.MovieSession;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieSessionMapper {
    MovieSessionMapper INSTANCE = Mappers.getMapper(MovieSessionMapper.class);

    MovieSession dtoToMovieSession(MovieSessionDto dto);

    MovieSessionDto movieSessionToDto(MovieSession entity);
}
