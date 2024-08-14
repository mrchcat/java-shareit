package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.CommentNewDTO;
import ru.practicum.shareit.item.dto.CommentOutputDTO;
import ru.practicum.shareit.item.dto.ItemNewDTO;
import ru.practicum.shareit.item.dto.ItemOutputDTO;
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
    public ItemOutputDTO createItem(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                    @RequestBody @Valid ItemNewDTO itemNewDTO) {
        log.info("Received request from userId={} to add item with parameters {}", userId, itemNewDTO);
        return itemService.createItem(userId, itemNewDTO);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemOutputDTO updateItem(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                    @PathVariable @Positive long itemId,
                                    @RequestBody @Valid ItemUpdateDTO itemUpdateDTO) {
        log.info("Received request from userId={} to update item with id={} and parameters {}", userId, itemId, itemUpdateDTO);
        return itemService.updateItem(userId, itemId, itemUpdateDTO);
    }


    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemOutputDTO getItem(@PathVariable @Positive long itemId) {
        log.info("Received request to get item with id= {}", itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemOutputDTO> getAllItems(@RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        log.info("Received request to get all items");
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ItemOutputDTO> searchItems(@RequestParam("text") String text) {
        log.info("Received request to get items with text={}", text);
        return itemService.searchItems(text);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutputDTO addComment(@RequestHeader("X-Sharer-User-Id") @Positive long userId,
                                       @PathVariable @Positive long itemId,
                                       @RequestBody @Valid CommentNewDTO comment) {
        log.info("Received request to add comment {} to item with id={}", comment, itemId);
        return itemService.addComment(userId, itemId, comment);
    }
}
