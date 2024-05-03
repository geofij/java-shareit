package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository storage;

    @Override
    public User getById(long id) {
        return storage.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));
    }

    @Override
    public User add(User user) {
        return storage.save(user);
    }

    @Override
    public User update(User user) {
        User userFromStorage = storage.findById(user.getId())
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));

        updateUserFromDto(user, userFromStorage);

        return storage.save(userFromStorage);
    }

    @Override
    public void deleteById(long id) {
        storage.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return storage.findAll();
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
