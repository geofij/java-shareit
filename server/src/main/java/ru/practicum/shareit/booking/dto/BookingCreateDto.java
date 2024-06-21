package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BookingCreateDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;
}
