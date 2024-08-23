package ru.practicum.shareit.item.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.comment.CommentDTO;

import java.util.Collection;

@Getter
@Setter
@Builder
public class ItemDTOForRequest {
    private long id;
    private String name;
    private long owner_id;
}
