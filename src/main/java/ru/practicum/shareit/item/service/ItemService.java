package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item get(Long id);

    Item add(Item item);

    Item update(Item item);

    void delete(Long id);

    List<Item> getAll();

    List<Item> search(String text);
}
