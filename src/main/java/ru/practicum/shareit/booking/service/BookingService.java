package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto getById(long bookingId, long userId);

    BookingResponseDto create(BookingCreateDto bookingDto, long userId);

    BookingResponseDto approveBooking(boolean isApproved, long bookingId, long userId);

    List<BookingResponseDto> getUserBookingsByState(long userId, String state, int from, int size);

    List<BookingResponseDto> getBookingsByItemsOwner(long ownerId, String state, int from, int size);
}
