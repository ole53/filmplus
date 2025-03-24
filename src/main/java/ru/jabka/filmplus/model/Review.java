package ru.jabka.filmplus.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Review {

    private Long userId;
    private String comment;
}