package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.item.Item;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class ItemDBRepository implements ItemRepository {
    private final JdbcTemplate jdbc;
    private final ItemRowMapper itemRowMapper;

    @Override
    public Item createItem(Item item) {
        String query = """
                INSERT INTO items (name, description, available, owner_id)
                VALUES (?,?,?,?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        int rowsCreated = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setBoolean(3, item.isAvailable());
            ps.setLong(4, item.getOwner());
            return ps;
        }, keyHolder);
        if (rowsCreated == 0) {
            throw new InternalServerException(String.format("%s was not created!", item));
        }
        var keys = keyHolder.getKeys();
        if (isNull(keys) || isNull(keys.get("id"))) {
            throw new InternalServerException(String.format("Internal error. Id was not found for %s!", item));
        }
        long itemId = (long) keyHolder.getKeys().get("id");
        item.setId(itemId);
        return item;
    }

    @Override
    public boolean doesUserOwnItem(long userId, long itemId) {
        String query = """
                SELECT COUNT(*)
                FROM items
                WHERE owner_id=? AND id=?
                """;
        Integer resultRows = jdbc.queryForObject(query, Integer.class, userId, itemId);
        if (isNull(resultRows)) {
            throw new InternalServerException(
                    String.format("Error when check if item id=%d below to userId=%d", itemId, userId));
        }
        return !resultRows.equals(0);
    }

    @Override
    public Optional<Item> getItem(long itemId) {
        String query = """
                SELECT id,name,description,available,owner_id, request_id
                FROM items
                WHERE id=?
                """;
        try {
            Item item = jdbc.queryForObject(query, itemRowMapper, itemId);
            return Optional.ofNullable(item);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Item> getAllItems(long userId) {
        String query = """
                SELECT id,name,description,available,owner_id, request_id
                FROM items
                WHERE owner_id=?
                """;
        return jdbc.query(query, itemRowMapper, userId);
    }

    @Override
    public boolean updateItem(long itemId, Map<String, Object> itemFields) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE items SET ");
        boolean isFirstParam = true;
        for (var entry : itemFields.entrySet()) {
            var columName = ItemModelToDBMapper.mapToDB(entry.getKey());
            if (columName.isEmpty()) {
                throw new InternalServerException("Mapping to DB error in item class for field " + entry.getKey());
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
        params.add(itemId);
        int rowsUpdated = jdbc.update(query.toString(), params.toArray());
        if (rowsUpdated == 0) {
            throw new InternalServerException(String.format("Item with id=%d was not updated", itemId));
        }
        return rowsUpdated > 0;
    }

    @Override
    public Collection<Item> searchItems(String text) {
        String query = """
                SELECT id,name,description,available,owner_id, request_id
                FROM items
                WHERE available IS TRUE
                      AND (LOWER(name) like CONCAT('%', ? , '%') OR LOWER(description) like CONCAT('%', ? , '%'))
                """;
        String textInLowerCase = text.toLowerCase();
        return jdbc.query(query, itemRowMapper, textInLowerCase, textInLowerCase);
    }
}
