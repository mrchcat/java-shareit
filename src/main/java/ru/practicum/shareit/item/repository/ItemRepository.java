package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ItemRepository {

    Item createItem(Item item);

    boolean updateItem(long itemId, Map<String, Object> itemFields);

    Optional<Item> getItem(long itemId);

    Collection<Item> getAllItems(long userId);

    Collection<Item> searchItems(String text);

    boolean doesUserOwnItem(long userId, long itemId);

}
