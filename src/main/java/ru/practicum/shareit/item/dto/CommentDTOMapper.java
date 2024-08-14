package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

public class CommentDTOMapper {
    public static CommentOutputDTO toDTO(Comment comment) {
        return CommentOutputDTO.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .build();
    }

    public static Comment fromNewDTO(User user, Item item, CommentNewDTO dto ) {
        return Comment.builder()
                .text(dto.getText())
                .item(item)
                .author(user)
                .build();
    }
}
