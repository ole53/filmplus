package ru.jabka.filmplus.model.UserExtraInfo;

import ru.jabka.filmplus.model.User;

import java.time.LocalDate;

public class Friend {

    private User friend;
    private LocalDate dateAdd;

    public Friend (User user) {
        this.friend = user;
        this.dateAdd = LocalDate.now();
    }

    public User getFriend() { return this.friend; }
}
