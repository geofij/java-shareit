package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User getById(long id);

    User add(User user);

    User update(User user);

    void deleteById(long id);

    List<User> getAll();
}
