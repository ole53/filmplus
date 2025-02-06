package ru.jabka.filmplus.model.FilmExtraInfo;

import ru.jabka.filmplus.model.User;

import java.time.LocalDateTime;

public class Like {

    private Boolean like;
    private User user;
    private LocalDateTime date;

    public Like(Boolean like, User user){
        this.like = like;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public Boolean getLike() { return this.like; }

    public User getUser() { return this.user; }
}
