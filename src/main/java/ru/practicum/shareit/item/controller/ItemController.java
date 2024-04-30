package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public Item add(@Valid @RequestBody ItemCreateDto item, @RequestHeader("X-Sharer-User-Id") long ownerId) {
        userService.getById(ownerId);

        Item newItem = ItemMapper.toItem(item, ownerId);

        return itemService.save(newItem);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestBody ItemUpdateDto item,
                       @RequestHeader("X-Sharer-User-Id") long ownerId,
                       @PathVariable("itemId") long id) {
        if (itemService.getById(id).getOwner().getId() != ownerId) {
            throw new UserIsNotOwnerException("Недостаточно прав для редактирования.");
        }

        Item newItem = ItemMapper.toItem(item, ownerId);

        newItem.setId(id);
        return itemService.update(newItem);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable("itemId") long id) {
        return itemService.getById(id);
    }

    @GetMapping
    public List<Item> getAll(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getAll(ownerId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam("text") String text) {
        return itemService.searchByText(text);
    }
}
