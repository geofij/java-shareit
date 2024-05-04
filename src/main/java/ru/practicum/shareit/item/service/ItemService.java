package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentCreatedResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseWithBookingAndCommentDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    Item getById(Long id);

    ItemResponseWithBookingAndCommentDto getById(Long id, Long user);

    @Transactional
    ItemResponseDto save(Item item);

    @Transactional
    ItemResponseDto update(Item item, Item itemUpdate);

    @Transactional
    void deleteById(Long id);

    List<ItemResponseWithBookingAndCommentDto> getAllOwnerItems(long ownerId);

    List<ItemResponseDto> searchByText(String text);

    @Transactional
    CommentCreatedResponseDto createNewComment(CommentCreateDto commentDto, long itemId, long userId);
}
