package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item get(Long id);

    Item add(Item user, Long ownerId);

    Item update(Item user, Long ownerId);

    void delete(Long id);

    List<Item> getAll();
}
