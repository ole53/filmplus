package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT = """
            INSERT INTO FILMPLUS.USER (name, email, login, birthday)
            VALUES (:name, :email, :login, :birthday)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE FILMPLUS.USER u
            SET u.name = :name, u.email = :email, u.login = :login, u.birthday = :birthday
            WHERE u.id = :id
            RETURNING *;
            """;

    private static final String GET_BY_ID = """
            SELECT * FROM FILMPLUS.USER u
            WHERE u.id = :id;
            """;

    @Transactional(rollbackFor = Exception.class)
    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    @Transactional(readOnly = true)
    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("birthday", user.getBirthday());

        return params;
    }
}