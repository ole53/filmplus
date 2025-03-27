package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.service.ReviewService;

@RestController
@RequestMapping("/api/v1/film/comments")
@Tag(name = "Добавление комментария к фильму")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить комментарий к фильму")
    public Review addCommentToFilm(@RequestBody final Review review) {
        return reviewService.create(review);
    }
}