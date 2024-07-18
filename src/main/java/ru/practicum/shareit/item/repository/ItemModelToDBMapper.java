package ru.practicum.shareit.item.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemModelToDBMapper {
    private static final Map<String, String> fieldToColumn = new HashMap<>();

    static {
        fieldToColumn.put("id", "id");
        fieldToColumn.put("name", "name");
        fieldToColumn.put("description", "description");
        fieldToColumn.put("available", "available");
        fieldToColumn.put("owner", "owner_id");
        fieldToColumn.put("request", "request_id");
    }


    public static Optional<String> mapToDB(String field) {
        return Optional.ofNullable(fieldToColumn.get(field));
    }
}
