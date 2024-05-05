package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentCreatedResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseWithBookingAndCommentDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getById(Long id);

    ItemResponseWithBookingAndCommentDto getById(Long id, Long user);

    ItemResponseDto save(Item item);

    ItemResponseDto update(Item item, Item itemUpdate);

    void deleteById(Long id);

    List<ItemResponseWithBookingAndCommentDto> getAllOwnerItems(long ownerId);

    List<ItemResponseDto> searchByText(String text);

    CommentCreatedResponseDto createNewComment(CommentCreateDto commentDto, long itemId, long userId);
}
