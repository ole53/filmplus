package ru.jabka.filmplus.model;

public class Review {

    private Long userId;
    private String comment;

    public Review(Long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }
}
