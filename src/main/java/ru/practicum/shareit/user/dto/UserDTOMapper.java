package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserDTOMapper {
    public static UserOutputDTO toDTO(User user) {
        return UserOutputDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User fromNewDTO(UserNewDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }
}
