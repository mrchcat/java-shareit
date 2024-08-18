package ru.practicum.shareit.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void hasBadEmail() {
        String email="blabla@bla.bla";
        Assertions.assertFalse(userRepository.hasEmail(email));
    }

    @Test
    @Sql(scripts = {"/test.sql"})
    void hasCorrectEmail() {
        Assertions.assertTrue(userRepository.hasEmail("anna@mail.ru"));
    }

}