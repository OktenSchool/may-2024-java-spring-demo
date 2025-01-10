package org.okten.may2024.demo.mapper;

import org.mapstruct.Mapper;
import org.okten.may2024.demo.dto.RegisterUserDto;
import org.okten.may2024.demo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(RegisterUserDto dto);
}
