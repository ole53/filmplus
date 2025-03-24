package ru.jabka.filmplus.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ReviewRequest {

    private ArrayList<Review> comments;
}