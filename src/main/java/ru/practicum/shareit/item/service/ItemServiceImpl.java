package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.item.dto.CommentDTOMapper;
import ru.practicum.shareit.item.dto.CommentNewDTO;
import ru.practicum.shareit.item.dto.CommentOutputDTO;
import ru.practicum.shareit.item.dto.ItemDTOMapper;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTOWithBookings;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.Validator;

import java.time.LocalDateTime;
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
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;
    private final ItemDTOMapper itemDTOMapper;

    @Override
    public ItemOutputDTO createItem(long userId, ItemNewDTO itemNewDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(String.format("User with id=%d does not exists", userId)));
        Item itemToCreate = itemDTOMapper.fromNewDTO(user, itemNewDTO);
        Item createdItem = itemRepository.save(itemToCreate);
        log.info("{} was created", createdItem);
        return itemDTOMapper.toDTO(createdItem);
    }

    @Override
    public ItemOutputDTO updateItem(long userId, long itemId, ItemUpdateDTO itemUpdateDTO) {
        Item oldItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Item with id=%d does not exists", itemId)));
        validator.validateIfUserOwnsItem(userId, itemId);

        Item itemToUpdate = fillInFieldsToUpdate(oldItem, itemUpdateDTO);
        Item updatedItem = itemRepository.save(itemToUpdate);
        log.info("{} was updated", updatedItem);
        return itemDTOMapper.toDTO(itemToUpdate);
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
    public ItemOutputDTOWithBookings getItem(long userId, long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        return item.map(i->itemDTOMapper.toDTOWithBookings(userId,i)).orElseThrow(
                () -> new IdNotFoundException("Item with id=" + itemId + " not found"));
    }

    @Override
    public Collection<ItemOutputDTOWithBookings> getAllItems(long userId) {
        validator.validateIfUserNotExists(userId);
        Collection<Item> items = itemRepository.getAllItems(userId);
        return items.stream()
                .map(i->itemDTOMapper.toDTOWithBookings(userId,i))
                .toList();
    }

    @Override
    public Collection<ItemOutputDTO> searchItems(String text) {
        if (isNull(text) || text.isBlank()) {
            return Collections.emptyList();
        }
        Collection<Item> items = itemRepository.searchItems(text);
        return items.stream()
                .map(itemDTOMapper::toDTO)
                .toList();
    }

    @Override
    public CommentOutputDTO addComment(long userId, long itemId, CommentNewDTO commentDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Item with id=%d does not exists", itemId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(String.format("User with id=%d does not exists", userId)));
        validator.validateIfUserBookedItem(userId, itemId);
        Comment comment= CommentDTOMapper.fromNewDTO(user, item, LocalDateTime.now(), commentDto);
        Comment newComment=commentRepository.save(comment);
        log.info("{} was added", newComment);
        return CommentDTOMapper.toDTO(newComment);
    }
}
