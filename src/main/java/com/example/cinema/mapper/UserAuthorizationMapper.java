package com.example.cinema.mapper;

import com.example.cinema.dto.UserAuthorizationDto;
import com.example.cinema.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAuthorizationMapper {
    UserAuthorizationMapper AUTH_INSTANCE = Mappers.getMapper(UserAuthorizationMapper.class);

    UserAuthorizationDto userToDto(User user);
    User dtoToUser(UserAuthorizationDto dto);
}
