package ru.practicum.shareit.request.storage;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestStorage {
    ItemRequest get(long id);

    ItemRequest add(ItemRequest request);

    ItemRequest update(ItemRequest request);

    void delete(long id);
}
