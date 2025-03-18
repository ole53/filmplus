package ru.jabka.filmplus.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
    private ArrayList<Long> friends;

    public User(final Long id, final String name, final String email, final String login, final LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        this.friends = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLogin() { return this.login; }

    public LocalDate getBirthday() { return this.birthday; }

    public ArrayList<Long> getFriends() { return this.friends; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public void setLogin(String login) { this.login = login; }

    public void addFriend(Long userId) { this.friends.add(userId); }
}