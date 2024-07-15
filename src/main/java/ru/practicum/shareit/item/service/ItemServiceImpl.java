package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDTOMapper;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.utils.Validator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final Validator validator;

    @Override
    public ItemOutputDTO createItem(long userId, ItemNewDTO itemNewDTO) {
        validator.validateIfUserNotExists(userId);
        Item itemToCreate = ItemDTOMapper.fromNewDTO(userId, itemNewDTO);
        Item createdItem = itemRepository.createItem(itemToCreate);
        log.info("{} was created", createdItem);
        return ItemDTOMapper.toDTO(createdItem);
    }

    @Override
    public ItemOutputDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO) {
        validator.validateIfUserOwnsItem(userId, itemId);
        Map<String, Object> fieldsToUpdate = fillInFieldsToUpdate(itemUpdateDTO);
        if (!fieldsToUpdate.isEmpty()) {
            boolean isUpdated = itemRepository.updateItem(itemId, fieldsToUpdate);
            if (!isUpdated) {
                throw new InternalServerException(String.format("Item with id=%d was not updated", itemId));
            }
        }
        Optional<Item> updatedItem = itemRepository.getItem(itemId);
        if (updatedItem.isEmpty()) {
            throw new InternalServerException(String.format("Item with id=%d was not updated", itemId));
        }
        log.info("{} was updated", updatedItem);
        return ItemDTOMapper.toDTO(updatedItem.get());
    }

    private Map<String, Object> fillInFieldsToUpdate(ItemUpdateDTO itemUpdateDTO) {
        Map<String, Object> fieldsToUpdate = new HashMap<>();
        String name = itemUpdateDTO.getName();
        if (nonNull(name)) {
            fieldsToUpdate.put("name", name);
        }
        String description = itemUpdateDTO.getDescription();
        if (nonNull(description)) {
            fieldsToUpdate.put("description", description);
        }
        Boolean available = itemUpdateDTO.getAvailable();
        if (nonNull(available)) {
            fieldsToUpdate.put("available", available);
        }
        return fieldsToUpdate;
    }

    @Override
    public ItemOutputDTO getItem(long itemId) {
        Optional<Item> item = itemRepository.getItem(itemId);
        return item.map(ItemDTOMapper::toDTO).orElseThrow(
                () -> new IdNotFoundException("Item with id=" + itemId + " not found"));
    }

    @Override
    public Collection<ItemOutputDTO> getAllItems(long userId) {
        validator.validateIfUserNotExists(userId);
        Collection<Item> items = itemRepository.getAllItems(userId);
        return items.stream()
                .map(ItemDTOMapper::toDTO)
                .toList();
    }

    @Override
    public Collection<ItemOutputDTO> searchItems(String text) {
        if (isNull(text) || text.isBlank()) {
            return Collections.emptyList();
        }
        Collection<Item> items = itemRepository.searchItems(text);
        return items.stream()
                .map(ItemDTOMapper::toDTO)
                .toList();
    }
}
