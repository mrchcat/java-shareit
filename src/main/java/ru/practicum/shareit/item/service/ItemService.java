package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentCreateDTO;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemCreateDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOWithBookings;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;

import java.util.Collection;

public interface ItemService {
    ItemDTO createItem(long userId, ItemCreateDTO itemCreateDTO);

    ItemDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO);

    ItemDTOWithBookings getItem(long userId, long itemId);

    Collection<ItemDTOWithBookings> getAllItems(long userId);

    Collection<ItemDTO> searchItems(String text);

    CommentDTO addComment(long userId, long itemId, CommentCreateDTO comment);
}
