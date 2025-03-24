package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.LikeRequest;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.model.ReviewRequest;
import ru.jabka.filmplus.model.User;

import java.util.ArrayList;
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

    public Film search(final String name, final Genre genre) {
        final Film film = films.stream()
                .filter(u -> u.getName().equalsIgnoreCase(name) && u.getGenres().equals(genre))
                .findFirst()
                .orElse(null);
        if (film == null) {
            throw new BadRequestException(String.format("Фильм: жанр - %s, название - %s не найден!", genre.name(), name));
        }
        return film;
    }

    public Film update(final Film film) {
        validate(film);
        final Film existFilm = getById(film.getId());

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

    public Film addComment(final Long userId, final Long filmId, final String comment) {
        if (userId == null) {
            throw new BadRequestException("Необходимо указать пользователя");
        }
        if (filmId == null) {
            throw new BadRequestException("Необходимо указать фильм!");
        }
        if (comment == null || comment.isEmpty()) {
            throw new BadRequestException("Комментарий не может быть пустым!");
        }
        final User user = UserService.getById(userId);
        final Film film = getById(filmId);

        final Review review = new Review();
        review.setUserId(userId);
        review.setComment(comment);

        final ArrayList<Review> arrReview = film.getComments().getComments();
        final ReviewRequest revReq = new ReviewRequest();
        arrReview.add(review);
        revReq.setComments(arrReview);

        film.setComments(revReq);

        return film;
    }

    public Film addLike(final Long userId, final Long filmId) {
        if (userId == null) {
            throw new BadRequestException("Необходимо указать id пользователя!");
        }
        if (filmId == null) {
            throw new BadRequestException("Необходимо указать id фильма!");
        }
        final User user = UserService.getById(userId);
        final Film film = getById(filmId);

        if (film.getUsersLikes().getLikes().contains(userId)) {
            throw new BadRequestException("Пользователь уже поставил лайк фильму!");
        }
        final LikeRequest likeReq = film.getUsersLikes();
        ArrayList<Long> arrLikes = likeReq.getLikes();
        arrLikes.add(userId);
        likeReq.setLikes(arrLikes);
        film.setUsersLikes(likeReq);

        return film;
    }
}