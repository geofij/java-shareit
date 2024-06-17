package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemClient.getItem(id, userId);
    }

    @PostMapping()
    public ResponseEntity<Object> save(@Valid @RequestBody ItemRequestDto item,
                                       @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemClient.postItem(item, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ItemUpdDto item,
                                         @RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable("id") long itemId) {
        return itemClient.patchItem(itemId, userId, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        itemClient.deleteItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemClient.getItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text) {
        if (text.isBlank()) return ResponseEntity.ok(List.of());
        return itemClient.searchItems(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addComment(@Valid @RequestBody CommentRequestDto comment,
                                             @RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable("id") long itemId) {
        return itemClient.addComment(itemId, userId, comment);
    }
}
