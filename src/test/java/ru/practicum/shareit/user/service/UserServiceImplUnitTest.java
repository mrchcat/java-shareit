package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.IdNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.Validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {
    @Mock
    Validator mockValidator;
    @Mock
    UserRepository mockUserRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    void deleteCorrectUserTest() {
        long correctUserId=10L;
        Mockito.doNothing().when(mockValidator).validateIfUserNotExists(correctUserId);
        InOrder inOrder=Mockito.inOrder(mockValidator,mockUserRepository);
        userServiceImpl.deleteUser(correctUserId);
        inOrder.verify(mockValidator, Mockito.times(1)).validateIfUserNotExists(correctUserId);;
        inOrder.verify(mockUserRepository, Mockito.times(1)).deleteById(correctUserId);
        Mockito.verifyNoMoreInteractions(mockValidator,mockUserRepository);
    }

    @Test
    void deleteIncorrectUserTest(){
        long badUserId=13L;
        Mockito.doThrow(IdNotFoundException.class).when(mockValidator).validateIfUserNotExists(badUserId);
        assertThrows(IdNotFoundException.class,()-> userServiceImpl.deleteUser(badUserId));
        Mockito.verify(mockUserRepository,Mockito.never()).deleteById(Mockito.any());
        Mockito.verifyNoMoreInteractions(mockValidator,mockUserRepository);
    }
}