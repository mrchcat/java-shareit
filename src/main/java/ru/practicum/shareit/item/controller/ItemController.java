package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentCreateDTO;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemCreateDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOWithBookings;
import ru.practicum.shareit.item.dto.ItemUpdateDTO;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO createItem(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                              @RequestBody @Valid ItemCreateDTO itemCreateDTO) {
        log.info("Received request from userId={} to add item with parameters {}", userId, itemCreateDTO);
        return itemService.createItem(userId, itemCreateDTO);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO updateItem(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                              @PathVariable @Positive long itemId,
                              @RequestBody @Valid ItemUpdateDTO itemUpdateDTO) {
        log.info("Received request from userId={} to update item with id={} and parameters {}", userId, itemId, itemUpdateDTO);
        return itemService.updateItem(userId, itemId, itemUpdateDTO);
    }


    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDTOWithBookings getItem(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                       @PathVariable @Positive long itemId) {
        log.info("Received request from userId={} to get item with id= {}", userId, itemId);
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDTOWithBookings> getAllItems(@RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        log.info("Received request from userId={} to get all items", userId);
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemDTO> searchItems(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                           @RequestParam("text") String text) {
        log.info("Received request from userId={} to get items with text={}", userId, text);
        return itemService.searchItems(text);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                 @PathVariable @Positive long itemId,
                                 @RequestBody @Valid CommentCreateDTO comment) {
        log.info("Received request from userId={} to add comment {} to itemId={}", userId, comment, itemId);
        return itemService.addComment(userId, itemId, comment);
    }
}
