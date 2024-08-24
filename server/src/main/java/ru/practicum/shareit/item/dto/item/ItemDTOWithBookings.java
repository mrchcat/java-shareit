package ru.practicum.shareit.item.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.dto.comment.CommentDTO;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Builder
@ToString
public class ItemDTOWithBookings {
    private long id;
    private String name;
    private String description;
    private boolean available;
    private Collection<CommentDTO> comments;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
}