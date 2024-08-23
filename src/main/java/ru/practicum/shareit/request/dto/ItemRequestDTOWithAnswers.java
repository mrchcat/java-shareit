package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.item.ItemDTOForRequest;
import ru.practicum.shareit.user.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class ItemRequestDTOWithAnswers {
    private long id;
    private String description;
    private LocalDateTime created;
    List<ItemDTOForRequest> items;
}
