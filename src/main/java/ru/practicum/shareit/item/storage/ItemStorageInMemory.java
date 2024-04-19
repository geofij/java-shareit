package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Repository
public class ItemStorageInMemory implements ItemStorage {
    private final Map<Long, Item> storage = new HashMap<>();
    private long id;

    @Override
    public Item getById(long id) {
        return storage.get(id);
    }

    @Override
    public Item save(Item item) {
        id++;
        item.setId(id);
        storage.put(item.getId(), item);
        return storage.get(item.getId());
    }

    @Override
    public Item update(Item item) {
        storage.replace(item.getId(), item);
        return getById(item.getId());
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public List<Item> getAll() {
        return new ArrayList<>(storage.values());
    }
}
