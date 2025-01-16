package ru.andreyszdlv.eventscheduler.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserResponseDto;
import ru.andreyszdlv.eventscheduler.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(RegisterUserRequestDto registerUserRequestDto);

    RegisterUserResponseDto toRegisterUserResponseDto(User user);
}
