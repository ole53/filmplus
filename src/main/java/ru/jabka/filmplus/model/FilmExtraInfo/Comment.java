package ru.jabka.filmplus.model.FilmExtraInfo;

import ru.jabka.filmplus.model.User;

import java.time.LocalDateTime;

public class Comment {

    private String comment;
    private User user;
    private LocalDateTime date;

    public Comment(String comment, User user){
        this.comment = comment;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public String getComment() { return comment; }

    public User getUser() { return user; }
}
