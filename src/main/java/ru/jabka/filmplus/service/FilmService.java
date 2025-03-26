package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.repository.FilmRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class FilmService {

    private static final HashSet<Film> films = new HashSet<>();

    private final FilmRepository filmRepository;

    public Film create(final Film film) {
        validate(film);

        return filmRepository.insert(film);
    }

    public Film getById(final Long id) {
        return filmRepository.getById(id);
    }

    public Film getByName(final String name) {
        return filmRepository.getByName(name);
    }

    public Film search(final String name, final Genre genre) {
        return filmRepository.searchFilm(name, genre);
    }

    public Film update(final Film film) {
        validate(film);

        return filmRepository.update(film);
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
}