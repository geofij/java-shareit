package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestService {
    ItemRequest get(ItemRequest user);

    ItemRequest add(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void delete(ItemRequest user);
}
