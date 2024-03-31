package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User get(Long id);

    User add(User user);

    User update(User user);

    void delete(Long id);

    List<User> getAll();
}
