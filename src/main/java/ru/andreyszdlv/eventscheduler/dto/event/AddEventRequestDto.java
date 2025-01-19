package ru.andreyszdlv.eventscheduler.dto.event;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AddEventRequestDto(

        @NotBlank
        @Size(min = 1, max = 100)
        String title,

        @Max(value = 255)
        String description,

        @NotNull
        Integer categoryId,

        @NotNull
        LocalDateTime time
) {
}
