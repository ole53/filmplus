package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.service.FilmService;

@RestController
@RequestMapping("/api/v1/film/comments")
@Tag(name = "Добавление комментария к фильму")
public class ReviewController {

    private final FilmService filmService;

    public ReviewController(final FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить комментарий к фильму")
    public Film addCommentToFilm(
            @RequestBody final Long userId,
            @RequestBody final Long filmId,
            @RequestBody final String comment
    ) {
        return filmService.addComment(userId, filmId, comment);
    }
}
