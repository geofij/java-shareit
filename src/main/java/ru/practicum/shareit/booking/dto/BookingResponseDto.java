package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemInBookingDto;
import ru.practicum.shareit.user.dto.UserInBookingDto;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class BookingResponseDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private UserInBookingDto booker;
    private ItemInBookingDto item;
}
