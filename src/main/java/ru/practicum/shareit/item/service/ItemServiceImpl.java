package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemStorage;

    @Override
    public Item getById(Long id) {
        return itemStorage.findById(id).get();
    }

    @Override
    public Item save(Item item) {
        return itemStorage.save(item);
    }

    @Override
    public Item update(Item item) {
        Item itemUpdate = itemStorage.findById(item.getId()).get();

        updateItemFromDto(item, itemUpdate);

        return itemStorage.save(itemUpdate);
    }

    @Override
    public void deleteById(Long id) {
        itemStorage.deleteById(id);
    }

    @Override
    public List<Item> getAll(long ownerId) {
        return itemStorage.findAll().stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchByText(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }

        return itemStorage.findAll().stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())
                        && item.getAvailable())
                .collect(Collectors.toList());
    }

    private void updateItemFromDto(Item itemDto, Item itemUpdate) {
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
