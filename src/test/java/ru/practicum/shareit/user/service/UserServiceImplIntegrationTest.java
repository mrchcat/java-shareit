package ru.practicum.shareit.user.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserCreateDTO;
import ru.practicum.shareit.user.dto.UserUpdateDTO;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@SpringJUnitConfig({UserServiceImpl.class})
//@DataJpaTest
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplIntegrationTest {
    private final UserService userService;
    private final EntityManager em;

    @Test
    @Order(1)
    void createUserTest() {
        String userName = "Petr";
        String userEmail = "petr@mail.com";
        UserCreateDTO newUser = UserCreateDTO.builder()
                .name(userName)
                .email(userEmail)
                .build();
        userService.createUser(newUser);
        TypedQuery<User> getUserQuery = em.createQuery("SELECT u FROM User AS u WHERE u.email=:email", User.class)
                .setParameter("email", userEmail);
        User savedUser = getUserQuery.getSingleResult();
        assertTrue(savedUser.getId() >= 0);
        assertEquals(userName, savedUser.getName());
        assertEquals(userEmail, savedUser.getEmail());
    }

    @Test
    @Order(2)
    void updateUserTest() {
        String userName = "Petr";
        String userEmail = "petr@mail.com";
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .name(userName)
                .email(userEmail)
                .build();
        userService.createUser(userCreateDTO);
        TypedQuery<User> getUserQuery = em
                .createQuery("SELECT u FROM User AS u WHERE u.email=:email", User.class)
                .setParameter("email", userEmail);
        long savedUserId = getUserQuery.getSingleResult().getId();
        String newUserName="Anna";
        String newUserEmail="anna@dot.com";
        UserUpdateDTO userUpdateDTO =UserUpdateDTO.builder()
                .name(newUserName)
                .email(newUserEmail)
                .build();
        userService.updateUser(savedUserId,userUpdateDTO);
        TypedQuery<User> getUpdatedUserQuery = em
                .createQuery("SELECT u FROM User AS u WHERE u.id=:userId", User.class)
                .setParameter("userId", savedUserId);
        User updatedUser=getUpdatedUserQuery.getSingleResult();
        assertEquals(savedUserId, updatedUser.getId());
        assertEquals(newUserName, updatedUser.getName());
        assertEquals(newUserEmail, updatedUser.getEmail());
    }


}
