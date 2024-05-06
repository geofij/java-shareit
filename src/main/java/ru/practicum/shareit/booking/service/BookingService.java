package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto getById(long bookingId, long userId);

    BookingResponseDto create(BookingCreateDto bookingDto, long userId);

    void deleteById(long id);

    BookingResponseDto approveBooking(boolean isApproved, long bookingId, long userId);

    List<BookingResponseDto> getUserBookingsByState(long userId, String state);

    List<BookingResponseDto> getBookingsByItemsOwner(long ownerId, String state);
}
