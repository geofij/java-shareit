package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.exception.AccessErrorException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemResponseDto add(@Valid @RequestBody ItemCreateDto item, @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.save(item, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto update(@RequestBody ItemUpdateDto item,
                                  @RequestHeader("X-Sharer-User-Id") long ownerId,
                                  @PathVariable("itemId") long id) {
        Item itemUpdate = itemService.getById(id);

        if (itemUpdate.getOwner().getId() != ownerId) {
            throw new AccessErrorException("Недостаточно прав для редактирования.");
        }

        Item newItem = ItemMapper.toItem(item, itemUpdate.getOwner());

        newItem.setId(id);
        return itemService.update(newItem, itemUpdate);
    }

    @GetMapping("/{itemId}")
    public ItemResponseWithBookingAndCommentDto getItemById(@PathVariable("itemId") long id,
                                                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getById(id, userId);
    }

    @GetMapping
    public List<ItemResponseWithBookingAndCommentDto> getAll(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getAllOwnerItems(ownerId);
    }

    @GetMapping("/search")
    public List<ItemResponseDto> search(@RequestParam("text") String text, @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.searchByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentCreatedResponseDto addNewCommentToItem(@Valid @RequestBody CommentCreateDto commentDto,
                                                         @PathVariable("itemId") long id,
                                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.createNewComment(commentDto, id, userId);
    }
}
