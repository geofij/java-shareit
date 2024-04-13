package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestService {
    ItemRequest get(long id);

    ItemRequest add(ItemRequest request);

    ItemRequest update(ItemRequest request);

    void delete(long id);
}
