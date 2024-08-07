package ru.practicum.shareit.user.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserNewDTOTest {
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeEach
    void initFilmController() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @AfterEach
    void closes() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Validation: add NewUserDTO with too long name")
    void testValidationBadName() {
        UserNewDTO userNewDTO = UserNewDTO.builder()
                .name("K".repeat(100))
                .email("www@sss.ru")
                .build();
        assertFalse(validator.validate(userNewDTO).isEmpty());
    }

    @Test
    @DisplayName("Validation: add NewUserDTO with correct name")
    void testValidationGoodName() {
        UserNewDTO userNewDTO = UserNewDTO.builder()
                .name("K".repeat(10))
                .email("www@sss.ru")
                .build();
        assertTrue(validator.validate(userNewDTO).isEmpty());
    }

}