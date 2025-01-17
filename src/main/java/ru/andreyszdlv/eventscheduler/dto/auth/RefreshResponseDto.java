package ru.andreyszdlv.eventscheduler.dto.auth;

import lombok.Builder;

@Builder
public record RefreshResponseDto(
        String accessToken,

        String refreshToken
) {
}
