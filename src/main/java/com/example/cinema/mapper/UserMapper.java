package com.example.cinema.mapper;

import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);
    User dtoToUser(UserDto dto);
}
