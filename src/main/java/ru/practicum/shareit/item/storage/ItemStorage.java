package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item getById(long id);

    Item save(Item user);

    Item update(Item user);

    void deleteById(long id);

    List<Item> getAll();
}
