package ru.andreyszdlv.eventscheduler.dto.auth;

public record LoginUserResponseDto(
        String accessToken,

        String refreshToken
) {
}
