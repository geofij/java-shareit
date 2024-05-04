package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingInItemDto;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static Item toItem(ItemCreateDto item, User owner) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(owner)
                .build();
    }

    public static Item toItem(ItemUpdateDto item, User owner) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(owner)
                .build();
    }

    public static ItemResponseWithBookingAndCommentDto toItemResponseDto(Item item,
                                                                         BookingInItemDto lastBooking,
                                                                         BookingInItemDto nextBooking,
                                                                         List<CommentCreatedResponseDto> comments) {
        return ItemResponseWithBookingAndCommentDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .comments(comments)
                .build();

    }
    public static ItemResponseWithBookingAndCommentDto toItemResponseDto(Item item,
                                                                         List<CommentCreatedResponseDto> comments) {
        return ItemResponseWithBookingAndCommentDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments(comments)
                .build();
    }

    public static ItemResponseDto toItemInfoDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static List<ItemResponseDto> mapItemInfoDto(List<Item> items) {
        List<ItemResponseDto> dtos = new ArrayList<>();

        for (Item item : items) {
            dtos.add(toItemInfoDto(item));
        }

        return dtos;
    }
}
