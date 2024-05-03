package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.dto.BookingInItemDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ItemResponseDto {
private Long id;
private String name;
private String description;
private Boolean available;
private BookingInItemDto lastBooking;
private BookingInItemDto nextBooking;
private List<CommentCreatedResponseDto> comments;
}
