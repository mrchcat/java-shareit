package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);

    boolean updateUser(long userId, Map<String, Object> userFields);

    boolean deleteUser(long userId);

    Optional<User> getUser(long userId);

    Collection<User> getAllUsers();

    boolean hasEmail(String email);

    boolean hasEmail(String email, long userId);

    boolean hasUserId(long userId);
}
