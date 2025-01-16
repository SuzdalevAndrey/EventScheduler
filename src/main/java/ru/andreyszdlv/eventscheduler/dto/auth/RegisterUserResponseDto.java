package ru.andreyszdlv.eventscheduler.dto.auth;


public record RegisterUserResponseDto(
        long id,

        String name,

        String email
){
}
