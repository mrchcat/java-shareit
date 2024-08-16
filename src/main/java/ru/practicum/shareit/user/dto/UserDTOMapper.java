package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserDTOMapper {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User fromNewDTO(UserCreateDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }
}
