package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentDTOMapper {
    public static CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Comment fromNewDTO(User user, Item item, LocalDateTime creationTime, CommentCreateDTO dto) {
        return Comment.builder()
                .text(dto.getText())
                .item(item)
                .author(user)
                .created(creationTime)
                .build();
    }
}
