package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class ItemNewDTO {
    @NotBlank(message = "name is empty")
    @Length(max = 50, message = "name must be less than 50 digits")
    private String name;

    @NotBlank(message = "name is empty")
    @Length(max = 200, message = "description must be less than 50 digits")
    private String description;

    @NotNull(message = "item availability is null")
//    @AssertTrue
    private Boolean available;
}
