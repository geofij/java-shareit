package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final RequestService service;
    private final UserService userService;

    @PostMapping
    public RequestResponseDto create(@Valid @RequestBody RequestCreateDto requestDto,
                                     @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.create(requestDto, userId);
    }

    @GetMapping
    public List<RequestResponseDto> getAllUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return service.getAllUserRequests(userId);
    }

    @GetMapping("/all")
    public List<RequestResponseDto> getAllRequests(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                                   @RequestParam(name = "size", defaultValue = "10") @Positive int size,
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
