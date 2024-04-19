package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static User toUser(UserUpdateDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserCreateDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
