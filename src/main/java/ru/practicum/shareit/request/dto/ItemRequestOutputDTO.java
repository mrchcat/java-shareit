package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.dto.UserOutputDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemRequestOutputDTO {
    private long id;
    private String description;
    private UserOutputDTO requestor;
    private LocalDateTime created;
}
