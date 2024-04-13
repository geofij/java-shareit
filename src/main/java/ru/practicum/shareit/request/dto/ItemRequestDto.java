package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ItemRequestDto {
    @NotNull
    private String description;

    @NotNull
    private Long requesterId;

    @NotNull
    private LocalDateTime created;
}
