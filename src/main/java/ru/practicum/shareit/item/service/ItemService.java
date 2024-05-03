package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentCreatedResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getById(Long id);

    ItemResponseDto getById(Long id, Long user);

    Item save(Item item);

    Item update(Item item, Item itemUpdate);

    void deleteById(Long id);

    List<ItemResponseDto> getAll(long ownerId);

    List<Item> searchByText(String text);

    CommentCreatedResponseDto createNewComment(CommentCreateDto commentDto, long itemId, long userId);
}
