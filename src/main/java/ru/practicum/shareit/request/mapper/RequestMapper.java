package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestCreateDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static RequestResponseDto toItemRequestDto(ItemRequest request, List<Item> items, LocalDateTime created) {
        RequestResponseDto dto = RequestResponseDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(created)
                .build();

        if (!items.isEmpty()) {
            dto.setItems(ItemMapper.mapItemInfoDto(items));
        } else {
            dto.setItems(new ArrayList<>());
        }

        return dto;
    }

    public static ItemRequest toItemRequest(RequestCreateDto itemRequest, User requester) {
        return ItemRequest.builder()
                .description(itemRequest.getDescription())
                .requester(requester)
                .build();
    }
}
