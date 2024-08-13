package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDTOMapper;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.utils.Validator;

import java.util.Collection;
import java.util.Collections;
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
        Item createdItem = itemRepository.save(itemToCreate);
        log.info("{} was created", createdItem);
        return ItemDTOMapper.toDTO(createdItem);
    }

    @Override
    public ItemOutputDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO) {
        Item oldItem=itemRepository.findById(itemId)
                .orElseThrow(()->new IdNotFoundException(String.format("Item with id=%d does not exists", itemId)));
        validator.validateIfUserOwnsItem(userId, itemId);

        Item itemToUpdate = fillInFieldsToUpdate(oldItem, itemUpdateDTO);
        Item updatedItem = itemRepository.save(itemToUpdate);
        log.info("{} was updated", updatedItem);
        return ItemDTOMapper.toDTO(itemToUpdate);
    }

    private Item fillInFieldsToUpdate(Item item, ItemUpdateDTO itemUpdateDTO) {
        String name = itemUpdateDTO.getName();
        if (nonNull(name)) {
            item.setName(name);
        }
        String description = itemUpdateDTO.getDescription();
        if (nonNull(description)) {
            item.setDescription(description);
        }
        Boolean available = itemUpdateDTO.getAvailable();
        if (nonNull(available)) {
            item.setAvailable(available);
        }
        return item;
    }

    @Override
    public ItemOutputDTO getItem(long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
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
