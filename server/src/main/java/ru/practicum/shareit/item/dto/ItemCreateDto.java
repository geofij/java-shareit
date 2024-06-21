package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ItemCreateDto {
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
