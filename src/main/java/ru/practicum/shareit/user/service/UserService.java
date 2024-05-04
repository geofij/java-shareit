package ru.practicum.shareit.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {
    User getUserById(long id);

    UserResponseDto getById(long id);

    @Transactional
    UserResponseDto add(User user);

    @Transactional
    UserResponseDto update(User user);

    @Transactional
    void deleteById(long id);

    List<UserResponseDto> getAll();
}
