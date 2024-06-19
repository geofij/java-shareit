package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final RequestService service;
    private final UserService userService;

    @PostMapping
    public RequestResponseDto create(@RequestBody RequestCreateDto requestDto,
                                     @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.create(requestDto, userId);
    }

    @GetMapping
    public List<RequestResponseDto> getAllUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return service.getAllUserRequests(userId);
    }

    @GetMapping("/all")
    public List<RequestResponseDto> getAllRequests(@RequestParam(name = "from", defaultValue = "0") int from,
                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                   @RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return service.getAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public RequestResponseDto getRequestById(@PathVariable("requestId") long requestId,
                                             @RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return service.getRequestById(requestId);
    }
}
