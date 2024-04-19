package ru.practicum.shareit.request.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class ItemRequest {
    private Long id;
    private String description;
    private Long requesterId;
    private LocalDateTime created;
}
