package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.FilmExtraInfo.Comment;
import ru.jabka.filmplus.model.FilmExtraInfo.Like;
import ru.jabka.filmplus.service.FilmService;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {

    private final FilmService filmService;

    public FilmController(final FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody final Film film) {
        return filmService.create(film);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить фильм по id")
    public Film get(@PathVariable final Long id) {
        return filmService.getById(id);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить фильм по названию")
    public Film get(@PathVariable final String name) { return filmService.getByName(name); }

    @PatchMapping
    @Operation(summary = "Обновление фильма")
    public Film update(@RequestBody final Film film) {
        return filmService.update(film);
    }

    @PatchMapping("/addComment")
    @Operation(summary = "Добавление комментария к фильму")
    public Film update(@RequestBody final Film film, @RequestBody final Comment comment) {
        return filmService.addComment(film, comment);
    }

    @PatchMapping("/addLike")
    @Operation(summary = "Добавление лайка к фильму")
    public Film update(@RequestBody final Film film, @RequestBody final Like like) {
        return filmService.addLike(film, like);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление фильма")
    public void delete(@PathVariable final Long id) {
        filmService.delete(id);
    }
}
