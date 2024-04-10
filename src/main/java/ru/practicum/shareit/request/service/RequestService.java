package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;

public interface RequestService {
    ItemRequest get(Long id);

    ItemRequest add(ItemRequest request);

    ItemRequest update(ItemRequest request);

    void delete(Long id);
}
