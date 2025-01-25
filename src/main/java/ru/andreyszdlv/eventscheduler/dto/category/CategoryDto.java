package ru.andreyszdlv.eventscheduler.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        int id,

        @NotBlank(message = "{validation.error.category.name.is_empty}")
        String name
) {
}
