package ru.practicum.shareit.item.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CommentDTO {
    private long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
