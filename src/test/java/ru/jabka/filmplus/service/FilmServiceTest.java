package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.repository.FilmRepository;
import ru.jabka.filmplus.model.Film;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void createFilm_valid() {
        Film film = getFilm();
        Mockito.when(filmRepository.insert(film)).thenReturn(film);

        Film result = filmService.create(film);

        assertThat(result).isEqualTo(film);
        verify(filmRepository).insert(film);
    }

    @Test
    void createFilm_withInvalidData_nullName_throwsBadRequestException() {
        Film film = getFilm();
        film.setName(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> filmService.create(film)
        );

        assertEquals("Укажите название фильма!", exception.getMessage());
        verify(filmRepository, never()).insert(any());
    }

    @Test
    void createFilm_withInvalidData_nullDuration_throwsBadRequestException() {
        Film film = getFilm();
        film.setDuration(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> filmService.create(film)
        );

        assertEquals("Необходимо указать продолжительность фильма!", exception.getMessage());
        verify(filmRepository, never()).insert(any());
    }

    private Film getFilm() {
        return Film.builder()
                .id(1L)
                .name("TestFilm")
                .genres(Genre.valueOf("Horror"))
                .duration(120L)
                .description("lalalala")
                .releaseDate(LocalDate.of(1980, 11, 20))
                .build();
    }
}