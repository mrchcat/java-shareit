package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class ItemUpdateDTO {
    @Length(min = 1, max = 50, message = "name must have 1-50 digits")
    private String name;
    @Length(min = 1, max = 200, message = "description must have 1-200 digits")
    private String description;
    private Boolean available;
}
