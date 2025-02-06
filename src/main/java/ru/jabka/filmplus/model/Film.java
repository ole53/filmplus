package ru.jabka.filmplus.model;

import ru.jabka.filmplus.model.FilmExtraInfo.Like;
import ru.jabka.filmplus.model.FilmExtraInfo.Comment;

import java.time.LocalDate;
import java.util.HashSet;

public class Film {

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Genre genres;
    private HashSet<Comment> comments;
    private HashSet<Like> likes;

    public Film (Long id, String name, String description, LocalDate releaseDate, Long duration, Genre genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public LocalDate getReleaseDate() { return this.releaseDate; }

    public Long getDuration() { return this.duration; }

    public Genre getGenres() { return this.genres; }

    public String getComments() { return this.comments.toString(); }

    public String getLikes() { return this.likes.toString(); }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public void setDuration(Long duration) { this.duration = duration; }

    public void setGenres(Genre genres) { this.genres = genres; }

    public void setComments(Comment comment) { this.comments.add(comment); }

    public void setLikes(Like like) {
        if (like.getLike()) {
            this.likes.add(like);
        } else {
            this.likes.remove(like);
        }
    }
}
