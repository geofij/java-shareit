package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ItemDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    private Long ownerId;
    private Long requestId;
}
