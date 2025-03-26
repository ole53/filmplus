package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Friend;
import ru.jabka.filmplus.repository.mapper.FriendMapper;

@Repository
@RequiredArgsConstructor
public class FriendRepository {

    private final FriendMapper friendMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO FILMPLUS.FRIEND (user_id, friend_id)
            VALUES (:user_id, :friend_id)
            RETURNING *;
            """;

    private static final String EXISTS = """
            SELECT * FROM FILMPLUS.FRIEND f
            where f.user_id = :user_id and f.friend_id = :friend_id
            RETURNING *;
            """;

    @Transactional(rollbackFor = Exception.class)
    public Friend insert(final Friend friend) {
        return jdbcTemplate.queryForObject(INSERT, friendToSql(friend), friendMapper);
    }

    @Transactional(readOnly = true)
    public Friend exists(final Friend friend) {
        try {
            return jdbcTemplate.queryForObject(EXISTS, friendToSql(friend), friendMapper);
        } catch (Exception e) {
            throw new BadRequestException("Пользователи уже находятся в друзьях!");
        }
    }

    private MapSqlParameterSource friendToSql(final Friend friend) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", friend.getUserId());
        params.addValue("friend_id", friend.getFriendId());

        return params;
    }
}