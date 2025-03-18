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
@RequestMapping("/api/v1/film/like")
@Tag(name = "Добавление лайков к фильму")
public class FilmLikeController {

    private final FilmService filmService;

    public FilmLikeController(final FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/add")
    @Operation(summary = "Поставить фильму лайк")
    public Film addLikeToFilm(@RequestBody final Long userId, @RequestBody final Long filmId) {
        return filmService.addLike(userId, filmId);
    }
}
