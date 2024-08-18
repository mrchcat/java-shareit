package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.user.dto.UserCreateDTO;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest2 {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper ;

    @Test
    @DisplayName("delete correct user")
    void deleteUserTest() throws Exception {
        long userId = 100L;
        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isNoContent());
        Mockito.verify(userService, times(1)).deleteUser(userId);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("update existing user")
    void createUserTest() throws Exception {
        long userId = 2;
        String userName = "Anna";
        String userEmail = "anna@mail.ru";

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .name(userName)
                .email(userEmail)
                .build();
        UserDTO userSavedDTO = UserDTO.builder()
                .id(userId)
                .name(userName)
                .email(userEmail)
                .build();
        Mockito.when(userService.createUser(userCreateDTO)).thenReturn(userSavedDTO);
        String json = objectMapper.writeValueAsString(userCreateDTO);
        mockMvc.perform(post("/users").contentType("application/json").content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(is(userId), Long.class))
                .andExpect(jsonPath("$.name").value(is(userName), String.class))
                .andExpect(jsonPath("$.email").value(is(userEmail), String.class));
        Mockito.verify(userService).createUser(Mockito.eq(userCreateDTO));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("create user with incorrect email")
    void tryToCreateIncorrectUser() throws Exception {
        String userName = "Anna";
        String userBadEmail = "annamail.ru";

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .name(userName)
                .email(userBadEmail)
                .build();

        String json = objectMapper.writeValueAsString(userCreateDTO);
        mockMvc.perform(post("/users").contentType("application/json").content(json))
                .andExpect(status().isBadRequest());
        Mockito.verify(userService, Mockito.never()).createUser(Mockito.any());
    }
}