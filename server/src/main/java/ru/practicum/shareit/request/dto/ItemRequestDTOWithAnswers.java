package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.item.ItemDTOForRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemRequestDTOWithAnswers {
    private long id;
    private String description;
    private LocalDateTime created;
    private List<ItemDTOForRequest> items;
}
