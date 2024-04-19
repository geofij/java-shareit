package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getById(Long id);

    Item save(Item item);

    Item update(Item item);

    void deleteById(Long id);

    List<Item> getAll(long ownerId);

    List<Item> searchByText(String text);
}
