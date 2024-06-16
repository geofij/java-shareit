package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
public class ItemCreateDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Boolean available;

    private Long requestId;
}
