package ru.jabka.filmplus.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Film {

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Genre genres;
    private ReviewRequest comments;
    private LikeRequest usersLikes;

    public Film (Long id, String name, String description, LocalDate releaseDate, Long duration, Genre genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.comments = new ReviewRequest();
        this.usersLikes = new LikeRequest();
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public LocalDate getReleaseDate() { return this.releaseDate; }

    public Long getDuration() { return this.duration; }

    public Genre getGenres() { return this.genres; }

    public ArrayList<Review> getComments() { return this.comments.getComments(); }

    public ArrayList<Long> getLikes() { return this.usersLikes.getLikes(); }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public void setDuration(Long duration) { this.duration = duration; }

    public void setGenres(Genre genres) { this.genres = genres; }

    public void setComment(Long userId, String comment) { this.comments.addComment(userId, comment); }

    public void like(Long userId) { this.usersLikes.like(userId); }

    public void unlike(Long userId) { this.usersLikes.unlike(userId); }
}
