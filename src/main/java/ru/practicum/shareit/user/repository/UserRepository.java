package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN TRUE ELSE FALSE END "+
            "FROM User AS u WHERE u.id<>?1 AND u.email=?2")
    boolean hasEmail(long userId, String email);

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN TRUE ELSE FALSE END "+
           "FROM User AS u WHERE u.email=?1")
    boolean hasEmail(String email);
}
