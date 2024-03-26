package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

public interface UserService {
    User get(User user);

    User add(User user);

    User update(User user);

    void delete(User user);
}
