package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
public class ItemDTO {
    private long id;
    private String name;
    private String description;
    private boolean available;
    private Collection<CommentDTO> comments;
}
