package ru.andreyszdlv.eventscheduler.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private long id;

    private String title;

    private String description;

    private int categoryId;

    private long authorId;

    private LocalDateTime time;
}
