package ru.practicum.shareit.item.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private Boolean isAvailable;
    private Long ownerId;
    private Long requestId;
}
