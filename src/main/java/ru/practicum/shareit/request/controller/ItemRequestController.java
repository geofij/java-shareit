package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.WrongStateException;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
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
    public List<RequestResponseDto> getAllRequests(@RequestParam(name = "from", defaultValue = "0") int from,
                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                   @RequestHeader("X-Sharer-User-Id") long userId) {
        validateFromSizeParams(from, size);
        userService.isUserExist(userId);

        return service.getAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public RequestResponseDto getRequestById(@PathVariable("requestId") long requestId,
                                             @RequestHeader("X-Sharer-User-Id") long userId) {
        userService.isUserExist(userId);

        return service.getRequestById(requestId);
    }

    private void validateFromSizeParams(int from, int size) {
        if (from < 0 || size <= 0) {
            throw new WrongStateException("Неверные значения параметров.");
        }
    }
}
