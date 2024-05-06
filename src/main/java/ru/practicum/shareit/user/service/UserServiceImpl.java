package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DataNotFoundException;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository storage;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return storage.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(long id) {
        return UserMapper.toUserResponseDto(storage.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден.")));
    }

    @Override
    @Transactional
    public UserResponseDto add(User user) {
        return UserMapper.toUserResponseDto(storage.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto update(User user) {
        User userFromStorage = storage.findById(user.getId())
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));

        updateUserFromDto(user, userFromStorage);

        return UserMapper.toUserResponseDto(storage.save(userFromStorage));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        storage.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {
        return UserMapper.mapUserResponseDto(storage.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public void isUserExist(Long userId) {
        storage.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Пользователь не найден."));

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
