package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingInItemDto;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
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

    public static Item toItem(ItemCreateDto item, User owner, ItemRequest request) {
        return Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(owner)
                .request(request)
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

    public static ItemResponseWithBookingAndCommentDto toItemFullInfoDto(Item item,
                                                                         BookingInItemDto lastBooking,
                                                                         BookingInItemDto nextBooking,
                                                                         List<CommentCreatedResponseDto> comments) {
        ItemResponseWithBookingAndCommentDto dto = ItemResponseWithBookingAndCommentDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .comments(comments)
                .build();

        if (item.getRequest() != null) {
            dto.setRequestId(item.getRequest().getId());
        }

        return dto;
    }

    public static ItemResponseWithBookingAndCommentDto toItemFullInfoDto(Item item,
                                                                         List<CommentCreatedResponseDto> comments) {
        ItemResponseWithBookingAndCommentDto dto = ItemResponseWithBookingAndCommentDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments(comments)
                .build();

        if (item.getRequest() != null) {
            dto.setRequestId(item.getRequest().getId());
        }

        return dto;
    }

    public static ItemResponseDto toItemInfoDto(Item item) {
        ItemResponseDto dto = ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();

        if (item.getRequest() != null) {
            dto.setRequestId(item.getRequest().getId());
        }

        return dto;
    }

    public static List<ItemResponseDto> mapItemInfoDto(List<Item> items) {
        List<ItemResponseDto> dtos = new ArrayList<>();

        for (Item item : items) {
            dtos.add(toItemInfoDto(item));
        }

        return dtos;
    }
}
