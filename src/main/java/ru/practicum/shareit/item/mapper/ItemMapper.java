package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getIsAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public static Item toItem(ItemDto item) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getIsAvailable())
                .requestId(item.getRequestId())
                .build();
    }
}
