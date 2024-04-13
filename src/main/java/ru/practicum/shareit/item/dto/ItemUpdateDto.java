package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ItemUpdateDto {
    private String name;

    private String description;

    private Boolean available;

    private Long requestId;
}
