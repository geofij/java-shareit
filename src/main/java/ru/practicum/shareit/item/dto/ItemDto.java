package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.item.model.Item;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private String description;
    private boolean isAvailable;
    private int ownerId;
    private int requestId;

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.isAvailable())
                .requestId(item.getRequestId())
                .build();
    }
}
