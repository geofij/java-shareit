package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public UserResponseDto add(@Valid @RequestBody UserCreateDto user) {
        User newUser = UserMapper.toUser(user);

        return service.add(newUser);
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@Valid @RequestBody UserUpdateDto user, @PathVariable("id") long id) {
        User newUser = UserMapper.toUser(user);
        newUser.setId(id);

        return service.update(newUser);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long id) {
        service.deleteById(id);
    }
}
