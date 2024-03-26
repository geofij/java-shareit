package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    User get(User user);

    User add(User user);

    User update(User user);

    void delete(User user);
}
