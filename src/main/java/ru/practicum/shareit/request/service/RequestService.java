package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestService {
    ItemRequest get(Long id);

    ItemRequest add(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void delete(Long id);
}
