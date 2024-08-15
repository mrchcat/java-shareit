package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentNewDTO;
import ru.practicum.shareit.item.dto.CommentOutputDTO;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTOWithBookings;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;

import java.util.Collection;

public interface ItemService {
    ItemOutputDTO createItem(long userId, ItemNewDTO itemNewDTO);

    ItemOutputDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO);

    ItemOutputDTOWithBookings getItem(long userId, long itemId);

    Collection<ItemOutputDTOWithBookings> getAllItems(long userId);

    Collection<ItemOutputDTO> searchItems(String text);

    CommentOutputDTO addComment(long userId, long itemId, CommentNewDTO comment);
}
