package ru.practicum.shareit.request.storage;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestStorage {
    ItemRequest get(Long id);

    ItemRequest add(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void delete(Long id);
}
