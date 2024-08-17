package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.dto.UserDTOMapper;

public class ItemRequestDTOMapper {
    public static ItemRequestDTO toDTO(ItemRequest itemRequest) {
        UserDTO userDTO = UserDTOMapper.toDTO(itemRequest.getRequestor());
        return ItemRequestDTO.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestor(userDTO)
                .created(itemRequest.getCreated())
                .build();
    }
}
