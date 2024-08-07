package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserNewDTO {
    @NotBlank(message = "name is empty")
    @Length(max = 50, message = "name must be less than 50 digits")
    private String name;
    @NotNull(message = "email is mandatory")
    @Email(message = "incorrect email")
    private String email;
}
