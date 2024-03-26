package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

public interface ItemStorage {
    Item get(Item user);

    Item add(Item user);

    Item update(Item user);

    void delete(Item user);
}
