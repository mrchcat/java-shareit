package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserNewDTO;
import ru.practicum.shareit.user.dto.UserOutputDTO;
import ru.practicum.shareit.user.dto.UserUpdateDTO;

import java.util.Collection;

public interface UserService {

    UserOutputDTO createUser(UserNewDTO user);

    UserOutputDTO updateUser(long userId, UserUpdateDTO user);

    void deleteUser(long userId);

    UserOutputDTO getUser(long userId);

    Collection<UserOutputDTO> getAllUsers();

}
