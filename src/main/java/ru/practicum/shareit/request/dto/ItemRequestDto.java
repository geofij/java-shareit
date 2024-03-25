package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.request.ItemRequest;

@Data
@EqualsAndHashCode(of = "id")
@SuperBuilder
@NoArgsConstructor
public class ItemRequestDto {
    private int id;
    private String description;
    private int requesterId;
    private LocalDateTime created;

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .description(itemRequest.getDescription())
                .requesterId(itemRequest.getRequesterId())
                .created(itemRequest.getCreated())
                .build();
    }
}
