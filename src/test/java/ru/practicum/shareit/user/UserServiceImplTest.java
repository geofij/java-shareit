package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final User user = User.builder()
            .id(1L)
            .email("user@user.ru")
            .name("user")
            .build();

    @Test
    void getById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        assertNotNull(user);
        assertEquals(userService.getById(1L).getEmail(), user.getEmail());
        assertThrows(DataNotFoundException.class, () -> userService.getById(2L));
    }

    @Test
    void update() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assert user != null;
        assertEquals(userService.add(user).getEmail(), "user@user.ru");

        user.setEmail("update@update.ru");
        assertEquals(userService.update(user).getEmail(), "update@update.ru");

        user.setId(2L);
        assertThrows(DataNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void deleteById() {
        assertDoesNotThrow(() -> userService.deleteById(1L));
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertEquals(userService.getAll().get(0).getEmail(), user.getEmail());
    }

    @Test
    void isUserExist() {
        assertThrows(DataNotFoundException.class, () -> userService.getById(2L));
    }
}