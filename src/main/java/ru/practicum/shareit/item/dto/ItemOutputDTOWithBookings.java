package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Builder
@ToString
public class ItemOutputDTOWithBookings {
    private long id;
    private String name;
    private String description;
    private boolean available;
    private Collection<CommentOutputDTO> comments;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
}
