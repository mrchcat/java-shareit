package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDTOMapper;
import ru.practicum.shareit.user.dto.UserNewDTO;
import ru.practicum.shareit.user.dto.UserOutputDTO;
import ru.practicum.shareit.user.dto.UserUpdateDTO;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.Validator;

import java.util.Collection;
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
        User createdUser = userRepository.save(userToCreate)  ;
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
        User oldUser=userRepository.findById(userId)
                .orElseThrow(()->new IdNotFoundException(String.format("User with id=%d does not exists", userId)));

        User userToUpdate = fillInFieldsToUpdate(oldUser, updateUserDTO);
        User updatedUser= userRepository.save(userToUpdate);
        log.info("{} was updated", updatedUser);
        return UserDTOMapper.toDTO(updatedUser);
    }

    private User fillInFieldsToUpdate(User user, UserUpdateDTO updateUserDTO) {
        String email = updateUserDTO.getEmail();
        if (nonNull(email)) {
            user.setEmail(email);
        }
        String name = updateUserDTO.getName();
        if (nonNull(name)) {
            user.setName(name);
        }
        return user;
    }

    @Override
    public void deleteUser(long userId) {
        validator.validateIfUserNotExists(userId);
        userRepository.deleteById(userId);
        log.info("User with id={} deleted", userId);
    }

    @Override
    public UserOutputDTO getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(UserDTOMapper::toDTO).orElseThrow(
                () -> new IdNotFoundException("User with id=" + userId + " not found"));
    }

    @Override
    public Collection<UserOutputDTO> getAllUsers() {
        Collection<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTOMapper::toDTO)
                .toList();
    }
}
