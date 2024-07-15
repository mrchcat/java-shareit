package ru.practicum.shareit.user.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserModelToDBMapper {

    private static final Map<String, String> fieldToColumn = new HashMap<>();

    static {
        fieldToColumn.put("id", "id");
        fieldToColumn.put("name", "name");
        fieldToColumn.put("email", "email");
    }

    public static Optional<String> mapToDB(String field) {
        return Optional.ofNullable(fieldToColumn.get(field));
    }
}
