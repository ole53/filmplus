package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Like;
import ru.jabka.filmplus.repository.mapper.LikeMapper;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final LikeMapper likeMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO FILMPLUS.LIKE (user_id, film_id)
            VALUES (:user_id, :film_id)
            RETURNING *;
            """;

    private static final String EXISTS = """
            SELECT * FROM FILMPLUS.LIKE l
            WHERE l.user_id = :user_id and l.film_id = :film_id
            RETURNING *;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Like insert(final Like like) {
        return jdbcTemplate.queryForObject(INSERT, likeToSql(like), likeMapper);
    }

    @Transactional(readOnly = true)
    public Like exists(final Like like) {
        try {
            return jdbcTemplate.queryForObject(EXISTS, likeToSql(like), likeMapper);
        } catch (Exception e) {
            throw new BadRequestException("Пользователь уже поставил лайк фильму!");
        }
    }

    private MapSqlParameterSource likeToSql(final Like like) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", like.getUserId());
        params.addValue("film_id", like.getFilmId());

        return params;
    }
}