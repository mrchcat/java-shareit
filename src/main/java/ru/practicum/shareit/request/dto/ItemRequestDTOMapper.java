package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDTOMapper;

public class ItemRequestDTOMapper {
    public static ItemRequestOutputDTO toDTO(ItemRequest itemRequest) {
        return ItemRequestOutputDTO.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestor(UserDTOMapper.toDTO(itemRequest.getRequestor()))
                .created(itemRequest.getCreated())
                .build();
    }
}
