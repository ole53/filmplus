package ru.jabka.filmplus.model;

import java.util.ArrayList;

public class LikeRequest {

    private ArrayList<Long> likes;

    public LikeRequest() {
        this.likes = new ArrayList<>();
    }

    public void like(Long userId) { this.likes.add(userId); }

    public void unlike(Long userId) { this.likes.remove(userId); }

    public ArrayList<Long> getLikes() { return this.likes; }
}
