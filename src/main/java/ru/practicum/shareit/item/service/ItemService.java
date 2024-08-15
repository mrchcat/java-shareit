package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentNewDTO;
import ru.practicum.shareit.item.dto.CommentOutputDTO;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;

import java.util.Collection;

public interface ItemService {
    ItemOutputDTO createItem(long userId, ItemNewDTO itemNewDTO);

    ItemOutputDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO);

    ItemOutputDTO getItem(long userId, long itemId);

    Collection<ItemOutputDTO> getAllItems(long userId);
    Collection<ItemOutputDTO> searchItems(long userId,String text);

    CommentOutputDTO addComment(long userId, long  itemId, CommentNewDTO comment);
}
