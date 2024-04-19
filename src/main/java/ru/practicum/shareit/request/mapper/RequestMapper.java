package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

public class RequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .description(itemRequest.getDescription())
                .requesterId(itemRequest.getRequesterId())
                .created(itemRequest.getCreated())
                .build();
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequest) {
        return ItemRequest.builder()
                .description(itemRequest.getDescription())
                .requesterId(itemRequest.getRequesterId())
                .created(itemRequest.getCreated())
                .build();
    }
}
