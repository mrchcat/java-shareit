package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.user.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDBRepository implements UserRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<User> userRowMapper;

    @Override
    public User createUser(User user) {
        String query = """
                INSERT INTO users (name, email)
                VALUES (?,?);
                """;
        var keyHolder = new GeneratedKeyHolder();
        int rowsCreated = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);
        if (rowsCreated == 0) {
            throw new InternalServerException(String.format("%s was not created!", user));
        }
        var keys = keyHolder.getKeys();
        if (isNull(keys) || isNull(keys.get("id"))) {
            throw new InternalServerException(String.format("Internal error. %s id was not found !", user));
        }
        long userId = (long) keyHolder.getKeys().get("id");
        user.setId(userId);
        return user;
    }

    @Override
    public boolean updateUser(long userId, Map<String, Object> userFields) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE users SET ");
        boolean isFirstParam = true;
        for (var entry : userFields.entrySet()) {
            var columName = UserModelToDBMapper.mapToDB(entry.getKey());
            if (columName.isEmpty()) {
                throw new InternalServerException("Mapping error in user class");
            }
            if (!isFirstParam) {
                query.append(", ");
            }
            isFirstParam = false;
            query.append(columName.get());
            query.append("=? ");
            params.add(entry.getValue());
        }
        query.append("WHERE id=?;");
        params.add(userId);
        int rowsUpdated = jdbc.update(query.toString(), params.toArray());
        if (rowsUpdated == 0) {
            throw new InternalServerException(String.format("User with id=%d was not updated", userId));
        }
        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteUser(long userId) {
        String query = """
                DELETE FROM users
                WHERE id=?
                """;
        int rowsDeleted = jdbc.update(query, userId);
        return rowsDeleted > 0;
    }

    @Override
    public Optional<User> getUser(long userId) {
        String query = """
                SELECT id,name,email
                FROM users
                WHERE id=?
                """;
        try {
            User user = jdbc.queryForObject(query, userRowMapper, userId);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        String query = """
                SELECT id,name,email
                FROM users
                """;
        return jdbc.query(query, userRowMapper);
    }

    @Override
    public boolean hasEmail(String email) {
        String query = """
                SELECT COUNT(*)
                FROM users
                WHERE email=?
                """;
        Integer numberOfEmails = jdbc.queryForObject(query, Integer.class, email);
        if (isNull(numberOfEmails)) {
            throw new InternalServerException("Error when check email");
        }
        return !numberOfEmails.equals(0);
    }

    @Override
    public boolean hasEmail(String email, long userId) {
        String query = """
                SELECT COUNT(*)
                FROM users
                WHERE id<>? AND email=?
                """;
        Integer numberOfEmails = jdbc.queryForObject(query, Integer.class, userId, email);
        if (isNull(numberOfEmails)) {
            throw new InternalServerException("Error when check email");
        }
        return !numberOfEmails.equals(0);
    }

    @Override
    public boolean hasUserId(long userId) {
        String query = """
                SELECT COUNT(*)
                FROM users
                WHERE id=?
                """;
        Integer resultRows = jdbc.queryForObject(query, Integer.class, userId);
        if (isNull(resultRows)) {
            throw new InternalServerException("Error when check userId");
        }
        return !resultRows.equals(0);
    }

}
