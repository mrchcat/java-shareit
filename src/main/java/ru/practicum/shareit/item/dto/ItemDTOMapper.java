package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.user.User;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ItemDTOMapper {
    private final CommentRepository commentRepository;

    public ItemOutputDTO toDTO(Item item) {
        Collection<Comment> comments=commentRepository.getCommentsByItem(item.getId());
        Collection<CommentOutputDTO> commentsDTO=comments.stream().map(CommentDTOMapper::toDTO).toList();
        return ItemOutputDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .comments(commentsDTO)
                .build();
    }

    public Item fromNewDTO(User user, ItemNewDTO itemNewDTO) {
        return Item.builder()
                .name(itemNewDTO.getName())
                .description(itemNewDTO.getDescription())
                .available(itemNewDTO.getAvailable())
                .owner(user)
                .build();
    }
}
