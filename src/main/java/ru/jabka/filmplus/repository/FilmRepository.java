package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.repository.mapper.FilmMapper;

@Repository
@RequiredArgsConstructor
public class FilmRepository {

    private final FilmMapper filmMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO FILMPLUS.FILM (title, description, release_date, duration, genre)
            VALUES (:title, :description, :release_date, :duration, :genre)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE FILMPLUS.FILM f
            SET f.title = :title,
                f.description = :description,
                f.release_date = :release_date,
                f.duration = :duration,
                f.genre = :genre
            WHERE f.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM FILMPLUS.FILM f
            WHERE f.id = :id;
            """;

    private static final String GET_BY_NAME = """
            SELECT * FROM FILMPLUS.FILM f
            WHERE f.name = :name;
            """;

    private static final String SEARCH_FILM = """
            SELECT * FROM FILMPLUS.FILM f
            WHERE lower(f.name) like lower('%:name%') and f.genre = :genre
            and rownum = 1;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Film insert(final Film film) {
        return jdbcTemplate.queryForObject(INSERT, filmToSql(film), filmMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Film update(final Film film) {
        return jdbcTemplate.queryForObject(UPDATE, filmToSql(film), filmMapper);
    }

    @Transactional(readOnly = true)
    public Film getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), filmMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с id %d не найден", id));
        }
    }

    @Transactional(readOnly = true)
    public Film getByName(final String name) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_NAME, new MapSqlParameterSource("name", name), filmMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с названием %s не найден", name));
        }
    }

    @Transactional(readOnly = true)
    public Film searchFilm(final String name, final Genre genre) {
        try {
            return jdbcTemplate.queryForObject(SEARCH_FILM,
                                               new MapSqlParameterSource()
                                                       .addValue("name", name)
                                                       .addValue("genre", genre.name()),
                                               filmMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с названием %s и жанром %s не найден!", name, genre.name()));
        }
    }

    private MapSqlParameterSource filmToSql(final Film film) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", film.getId());
        params.addValue("title", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release_date", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("genre", film.getGenres().name());

        return params;
    }
}