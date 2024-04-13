package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.exception.EmailUsedValidationException;
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
    public User add(@Valid @RequestBody UserCreateDto user) {
        User newUser = UserMapper.toUser(user);

        validateEmailNotUsed(user.getEmail());

        return service.add(newUser);
    }

    @PatchMapping("/{id}")
    public User update(@Valid @RequestBody UserUpdateDto user, @PathVariable("id") long id) {
        User newUser = UserMapper.toUser(user);

        if (newUser.getEmail() != null && !isSameEmail(newUser.getEmail(), id)) {
            validateEmailNotUsed(user.getEmail());
        }

        newUser.setId(id);

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

    private void validateEmailNotUsed(String email) {
        List<String> emails = service.getAll().stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList());

        for (String userEmail: emails) {
            if (userEmail.equals(email)) {
                throw new EmailUsedValidationException("Почта уже занята.");
            }
        }
    }

    private boolean isSameEmail(String email, long id) {
        return service.get(id).getEmail().equals(email);
    }
}
