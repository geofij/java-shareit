package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;

import java.util.List;

public interface RequestService {
    RequestResponseDto create(RequestCreateDto request, long userId);

    List<RequestResponseDto> getAllUserRequests(long userId);

    List<RequestResponseDto> getAllRequests(int from, int size, long userId);

    RequestResponseDto getRequestById(long requestId);
}
