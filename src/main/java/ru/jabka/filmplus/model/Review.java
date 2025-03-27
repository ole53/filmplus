package ru.jabka.filmplus.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Review {

    private Long id;
    private Long userId;
    private Long filmId;
    private String comment;
    private LocalDate date_create;
}