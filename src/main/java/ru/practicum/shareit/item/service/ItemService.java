package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

public interface ItemService {
    Item get(Item user);

    Item add(Item user);

    Item update(Item user);

    void delete(Item user);
}
