package ru.andreyszdlv.eventscheduler.dto.event;

import java.time.LocalDateTime;

public record EventDto(
        long id,

        String title,

        String description,

        int categoryId,

        long authorId,

        LocalDateTime time
) {
}
