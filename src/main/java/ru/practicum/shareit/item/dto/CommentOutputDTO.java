package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CommentOutputDTO {
    long id;
    String text;
    String authorName;
    LocalDateTime created;
}
