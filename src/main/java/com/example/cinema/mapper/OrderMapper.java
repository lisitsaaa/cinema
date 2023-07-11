package com.example.cinema.mapper;

import com.example.cinema.dto.OrderDto;
import com.example.cinema.entity.cinema.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order dtoToOrder(OrderDto dto);
}
