package ru.jabka.filmplus.model;

import java.util.ArrayList;

public class ReviewRequest {

    private ArrayList<Review> comments;

    public ReviewRequest() {
        this.comments = new ArrayList<>();
    }

    public void addComment(Long userId, String comment) {
        Review newComment = new Review(userId, comment);
        this.comments.add(newComment);
    }

    public ArrayList<Review> getComments() { return this.comments; }
}