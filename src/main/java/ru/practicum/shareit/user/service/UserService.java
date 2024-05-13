package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    UserResponseDto getById(long id);

    UserResponseDto add(User user);

    UserResponseDto update(User user);

    void deleteById(long id);

    List<UserResponseDto> getAll();

    void isUserExist(Long userId);
}
