package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.LikeRepository;
import ru.jabka.filmplus.model.Like;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final FilmService filmService;

    public Like create(final Like like) {
        validate(like);
        return likeRepository.insert(like);
    }

    private void validate(final Like like) {
        if (like.getUserId() == null) {
            throw new BadRequestException("Необходимо указать id пользователя!");
        }

        if (like.getFilmId() == null) {
            throw new BadRequestException("Необходимо указать id фильма!");
        }

        User userExists = userService.getById(like.getUserId());
        Film filmExists = filmService.getById(like.getFilmId());
        Like likeExists = likeRepository.exists(like);
    }
}