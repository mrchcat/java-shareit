package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.Comment;

import java.util.Collection;

@Getter
@Setter
@Builder
public class ItemOutputDTO {
    private long id;
    private String name;
    private String description;
    private boolean available;
    private Collection<CommentOutputDTO> comments;
}
