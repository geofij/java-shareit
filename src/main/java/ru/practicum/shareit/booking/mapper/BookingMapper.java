package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .itemId(booking.getItemId())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingDto booking) {
        return Booking.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .itemId(booking.getItemId())
                .status(booking.getStatus())
                .build();
    }
}
