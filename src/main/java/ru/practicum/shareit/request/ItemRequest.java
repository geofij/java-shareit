package ru.practicum.shareit.request;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.item.model.Item;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class ItemRequest {
    private int id;
    private String description;
    private int requesterId;
    private LocalDateTime created;
}
