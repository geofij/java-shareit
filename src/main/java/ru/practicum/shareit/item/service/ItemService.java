package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item get(Long id);

    Item add(Item user, Long ownerId);

    Item update(Item user, Long ownerId);

    void delete(Long id);

    List<Item> getAll();

    List<Item> search(String text);
}
