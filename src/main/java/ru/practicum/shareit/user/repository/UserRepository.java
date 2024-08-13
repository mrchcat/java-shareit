package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(*) FROM users WHERE email=?1")
    boolean hasEmail(String email);

    @Query("SELECT COUNT(*) FROM users WHERE email=?1 AND id<>?2")
    boolean hasEmail(String email, long userId);

}
