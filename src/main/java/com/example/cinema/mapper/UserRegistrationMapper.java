package com.example.cinema.mapper;

import com.example.cinema.dto.UserRegistrationDto;
import com.example.cinema.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRegistrationMapper {
    UserRegistrationMapper REG_INSTANCE = Mappers.getMapper(UserRegistrationMapper.class);

    UserRegistrationDto userToDto(User user);
    User dtoToUser(UserRegistrationDto dto);
}
