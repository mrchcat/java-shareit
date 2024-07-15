package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDTOMapper;
import ru.practicum.shareit.user.dto.UserNewDTO;
import ru.practicum.shareit.user.dto.UserOutputDTO;
import ru.practicum.shareit.user.dto.UserUpdateDTO;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.Validator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Validator validator;

    @Override
    public UserOutputDTO createUser(UserNewDTO userNewDTO) {
        validator.validateIfEmailIsUnique(userNewDTO.getEmail());
        User userToCreate = UserDTOMapper.fromNewDTO(userNewDTO);
        User createdUser = userRepository.createUser(userToCreate);
        log.info("{} was created", createdUser);
        return UserDTOMapper.toDTO(createdUser);
    }

    @Override
    public UserOutputDTO updateUser(long userId, UserUpdateDTO updateUserDTO) {
        validator.validateIfUserNotExists(userId);
        String email = updateUserDTO.getEmail();
        if (nonNull(updateUserDTO.getEmail())) {
            validator.validateIfEmailIsUnique(email, userId);
        }
        Map<String, Object> fieldsToUpdate = fillInFieldsToUpdate(updateUserDTO);
        if (!fieldsToUpdate.isEmpty()) {
            boolean isUpdated = userRepository.updateUser(userId, fieldsToUpdate);
            if (!isUpdated) {
                throw new InternalServerException(String.format("User with id=%d was not updated", userId));
            }
        }
        Optional<User> updatedUser = userRepository.getUser(userId);
        if (updatedUser.isEmpty()) {
            throw new InternalServerException(String.format("User with id=%d was not updated", userId));
        }
        log.info("{} was updated", updatedUser);
        return UserDTOMapper.toDTO(updatedUser.get());
    }

    private Map<String, Object> fillInFieldsToUpdate(UserUpdateDTO updateUserDTO) {
        Map<String, Object> fieldsToUpdate = new HashMap<>();
        String email = updateUserDTO.getEmail();
        if (nonNull(email)) {
            fieldsToUpdate.put("email", email);
        }
        String name = updateUserDTO.getName();
        if (nonNull(name)) {
            fieldsToUpdate.put("name", name);
        }
        return fieldsToUpdate;
    }

    @Override
    public void deleteUser(long userId) {
        validator.validateIfUserNotExists(userId);
        boolean isDeleted = userRepository.deleteUser(userId);
        if (!isDeleted) {
            throw new InternalServerException(String.format("User with id=%d was not deleted", userId));
        }
        log.info("User with id={} deleted", userId);
    }

    @Override
    public UserOutputDTO getUser(long userId) {
        Optional<User> user = userRepository.getUser(userId);
        return user.map(UserDTOMapper::toDTO).orElseThrow(
                () -> new IdNotFoundException("User with id=" + userId + " not found"));
    }

    @Override
    public Collection<UserOutputDTO> getAllUsers() {
        Collection<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(UserDTOMapper::toDTO)
                .toList();
    }
}
