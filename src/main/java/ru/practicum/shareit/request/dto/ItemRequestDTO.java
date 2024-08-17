package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.dto.UserDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemRequestDTO {
    private long id;
    private String description;
    private UserDTO requestor;
    private LocalDateTime created;
}
