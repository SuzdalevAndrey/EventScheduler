package ru.andreyszdlv.eventscheduler.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDto(

        @NotBlank(message = "{validation.error.token.is_empty}")
        String refreshToken

) {
}
