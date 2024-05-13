package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getById(Long id);

    ItemResponseWithBookingAndCommentDto getById(Long id, Long user);

    ItemResponseDto save(ItemCreateDto item, Long userId);

    ItemResponseDto update(Item item, Item itemUpdate);

    List<ItemResponseWithBookingAndCommentDto> getAllOwnerItems(long ownerId);

    List<ItemResponseDto> searchByText(String text);

    CommentCreatedResponseDto createNewComment(CommentCreateDto commentDto, long itemId, long userId);
}
