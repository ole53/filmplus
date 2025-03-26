package ru.jabka.filmplus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid(){
        final User user = getUser();
        Mockito.when(userRepository.insert(user)).thenReturn(user);

        User result = userService.create(user);

        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void createUser_withInvalidData_nullName_throwsBadRequestException() {
        final User user = getUser();
        user.setName(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );

        assertEquals("Укажите имя пользователя!", exception.getMessage());
        verify(userRepository,never()).insert(any());
    }

    @Test
    void createUser_withInvalidData_nullEmail_throwsBadRequestException() {
        final User user = getUser();
        user.setEmail(null);

        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.create(user)
        );

        assertEquals("Необходимо указать адрес электронной почты пользователя!", exception.getMessage());
        verify(userRepository,never()).insert(any());
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .name("UserName")
                .login("UserLogin")
                .birthday(LocalDate.of(1990, 2, 24))
                .email("user@dpd.ru")
                .build();
    }
}