package ru.andreyszdlv.eventscheduler.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private int id;

    private String name;

    private long authorId;
}
