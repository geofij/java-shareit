package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage storage;

    @Override
    public User get(Long id) {
        return storage.get(id);
    }

    @Override
    public User add(User user) {
        return storage.add(user);
    }

    @Override
    public User update(User user) {
        User userFromStorage = storage.get(user.getId());

        updateUserFromDto(user, userFromStorage);

        return storage.update(userFromStorage);
    }

    @Override
    public void delete(Long id) {
        storage.delete(id);
    }

    @Override
    public List<User> getAll() {
        return storage.getAll();
    }

    private void updateUserFromDto(User userDto, User updatedUser) {
        if (userDto.getName() != null) {
            updatedUser.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            updatedUser.setEmail(userDto.getEmail());
        }
    }
}
