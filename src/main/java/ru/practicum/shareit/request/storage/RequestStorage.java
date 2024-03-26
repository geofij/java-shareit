package ru.practicum.shareit.request.storage;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestStorage {
    ItemRequest get(ItemRequest user);

    ItemRequest add(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void delete(ItemRequest user);
}
