package ru.jabka.filmplus.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
    private ArrayList<Long> friends;
}