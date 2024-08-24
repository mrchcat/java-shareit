package ru.practicum.shareit.item.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemUpdateDTO {
    private String name;
    private String description;
    private Boolean available;
}
