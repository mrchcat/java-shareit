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
    long id;
    String text;
    String authorName;
    LocalDateTime created;
}
