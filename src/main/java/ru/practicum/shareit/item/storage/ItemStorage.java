package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item get(Long id);

    Item add(Item user);

    Item update(Item user);

    void delete(Long id);

    List<Item> getAll();
}
