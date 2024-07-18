package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.Item;

public class ItemDTOMapper {
    public static ItemOutputDTO toDTO(Item item) {
        return ItemOutputDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .build();
    }

    public static Item fromNewDTO(long userId, ItemNewDTO itemNewDTO) {
        return Item.builder()
                .name(itemNewDTO.getName())
                .description(itemNewDTO.getDescription())
                .available(itemNewDTO.getAvailable())
                .owner(userId)
                .build();
    }
}
