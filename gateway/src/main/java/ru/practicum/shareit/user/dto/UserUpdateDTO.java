package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserUpdateDTO {
    @Length(min = 1, max = 50, message = "name must have 1-50 digits")
    private String name;
    @Email(message = "incorrect email")
    private String email;
}
