package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserDto userDto;
    private final UserService service;

    @PostMapping
    public User add(@Valid @RequestBody UserDto user) {
        User newUser = UserMapper.toUser(user);
        return service.add(newUser);
    }

    @PatchMapping
    public User update(@Valid @RequestBody UserDto user) {
        User newUser = UserMapper.toUser(user);
        return service.update(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return service.get(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long id) {
        service.delete(id);
    }
}
