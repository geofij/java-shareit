package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;

    @Override
    public Item get(Long id) {
        return itemStorage.get(id);
    }

    @Override
    public Item add(Item item) {
        return itemStorage.add(item);
    }

    @Override
    public Item update(Item item) {
        Item itemUpdate = itemStorage.get(item.getId());

        updateItemFromDto(item, itemUpdate);

        return itemStorage.update(itemUpdate);
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }

    @Override
    public List<Item> getAll(long ownerId) {
        return itemStorage.getAll().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }

        return itemStorage.getAll().stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())
                        && item.getAvailable())
                .collect(Collectors.toList());
    }

    private void updateItemFromDto (Item itemDto, Item itemUpdate) {
        if (itemDto.getName() != null) {
            itemUpdate.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemUpdate.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            itemUpdate.setAvailable(itemDto.getAvailable());
        }
    }
}
