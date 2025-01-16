package ru.andreyszdlv.eventscheduler.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserRequestDto(

        @NotBlank(message = "{validation.error.user.email.is_empty}")
        @Email(message = "{validation.error.user.email.invalid}")
        @Size(max = 255, message = "{validation.error.user.email.size.invalid}")
        String email,

        @NotBlank(message = "{validation.error.user.password.is_empty}")
        @Size(min = 6, max = 100, message = "{validation.error.user.password.size.invalid}")
        String password
) {
}
