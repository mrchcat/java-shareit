package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {UserRepository.class})
@ComponentScan(basePackages = {"ru.practicum.shareit"})
class UserDBRepositoryTest {
    private final UserRepository userRepository;

    @Test
    @DisplayName("add user")
    void getCommonFilmsTest() {
        User userToAdd = User.builder()
                .name("Anna")
                .email("anna@mail.ru")
                .build();
        User receivedUser = userRepository.createUser(userToAdd);
        assertEquals(1, receivedUser.getId());
        assertEquals(userToAdd.getName(), receivedUser.getName());
        assertEquals(userToAdd.getEmail(), receivedUser.getEmail());
    }


}