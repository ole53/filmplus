package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.service.LikeService;

@RestController
@RequestMapping("/api/v1/film/like")
@Tag(name = "Добавление лайков к фильму")
public class FilmLikeController {

    private final LikeService likeService;

    public FilmLikeController(final LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/add")
    @Operation(summary = "Поставить фильму лайк")
    public Like addLikeToFilm(@RequestBody final Like like) {
        return likeService.create(like);
    }
}