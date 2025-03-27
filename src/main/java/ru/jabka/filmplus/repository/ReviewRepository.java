package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.mapper.ReviewMapper;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final ReviewMapper reviewMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO FILMPLUS.REVIEW (user_id, film_id, review_text)
            VALUES (:user_id, :film_id, :review_text)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE FILMPLUS.REVIEW r
            SET r.review_text = :review_text,
                r.date_create = sysdate
            WHERE r.id = :id
            RETURNING *;
            """;

    private static final String EXISTS = """
            SELECT FILMPLUS.REVIEW r
            WHERE r.user_id = :user_id and r.film_id = :film_id
            RETURNING *;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Review insert(final Review review) {
        return jdbcTemplate.queryForObject(INSERT, reviewToSql(review), reviewMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public Review update(final Review review) {
        return jdbcTemplate.queryForObject(UPDATE, reviewToSql(review), reviewMapper);
    }

    @Transactional(readOnly = true)
    public Review exists(final Review review) {
        try {
            return jdbcTemplate.queryForObject(EXISTS, reviewToSql(review), reviewMapper);
        } catch (Exception e) {
            throw new BadRequestException("Пользователь уже добавил комментарий к фильму!");
        }
    }

    private MapSqlParameterSource reviewToSql(final Review review) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", review.getId());
        params.addValue("user_id", review.getUserId());
        params.addValue("film_id", review.getFilmId());
        params.addValue("review_text", review.getComment());
        params.addValue("date_create", review.getDate_create());

        return params;
    }
}