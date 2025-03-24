package ru.jabka.filmplus.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LikeRequest {

    private ArrayList<Long> likes;
}