package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.FilmExtraInfo.Like;
import ru.jabka.filmplus.model.FilmExtraInfo.Comment;
import ru.jabka.filmplus.model.User;

import java.util.HashSet;

@Service
public class FilmService {

    private static final HashSet<Film> films = new HashSet<>();

    public Film create(final Film film) {
        validate(film);
        film.setId((long) films.size() + 1);
        films.add(film);
        return film;
    }

    public Film getById(final Long id) {
        final Film film = films.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (film == null) {
            throw new BadRequestException(String.format("Фильм с id %d не найден", id));
        }
        return film;
    }

    public Film getByName(final String name) {
        final Film film = films.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(null);
        if (film == null) {
            throw new BadRequestException(String.format("Фильм с названием %s не найден", name));
        }
        return film;
    }

    public Film update(final Film film) {
        validate(film);
        final Film existFilm = getById(film.getId());
        if (existFilm == null) {
            return null;
        }
        existFilm.setName(film.getName());
        existFilm.setDescription(film.getDescription());
        existFilm.setDuration(film.getDuration());
        existFilm.setGenres(film.getGenres());
        existFilm.setReleaseDate(film.getReleaseDate());
        return existFilm;
    }

    public void delete(final Long id) {
        films.remove(getById(id));
    }

    private void validate(final Film film) {
        if (film == null) {
            throw new BadRequestException("Введите информацию о фильме");
        }
        if (!StringUtils.hasText(film.getName())) {
            throw new BadRequestException("Укажите название фильма!");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new BadRequestException("Необходимо добавить описание фильма!");
        }
        if (film.getDuration() == null) {
            throw new BadRequestException("Необходимо указать продолжительность фильма!");
        }
        if (film.getGenres() == null) {
            throw new BadRequestException("Необходимо указать жанр фильма!");
        }
        if (film.getReleaseDate() == null) {
            throw new BadRequestException("Необходимо указать год выпуска фильма!");
        }
    }

    public Film addComment(final Film film, final Comment comment) {
        if (film == null) {
            throw new BadRequestException("Необходимо указать фильм!");
        }
        if (comment == null) {
            throw new BadRequestException("Необходимо указать комментарий!");
        }
        final Film existFilm = getById(film.getId());
        existFilm.setComments(comment);

        return existFilm;
    }

    public Film addLike(final Film film, Like like) {
        if (film == null) {
            throw new BadRequestException("Необходимо указать id фильма!");
        }
        if (like == null) {
            throw new BadRequestException("Необходимо указать id пользователя!");
        }
        final Film existFilm = getById(film.getId());
        existFilm.setLikes(like);

        return existFilm;
    }
}
