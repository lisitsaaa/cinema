package com.example.cinema.mapper;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.entity.cinema.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie dtoToMovie(MovieDto dto);
}
