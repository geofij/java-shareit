package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    @PostMapping
    public Item add(@Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") long ownerId) {
        Item newItem = ItemMapper.toItem(item);
        newItem.setOwnerId(ownerId);
        return service.add(newItem);
    }

    @PatchMapping("/{itemId}")
    public Item update(@Valid @RequestBody ItemDto item,
                       @RequestHeader("X-Sharer-User-Id") long ownerId,
                       @PathVariable("id") long id) {
        Item newItem = ItemMapper.toItem(item);
        newItem.setOwnerId(ownerId);
        newItem.setId(id);
        return service.update(newItem);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable("id") long id) {
        return service.get(id);
    }

    @GetMapping
    public List<Item> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam("text") String text) {
        return service.search(text);
    }
}
