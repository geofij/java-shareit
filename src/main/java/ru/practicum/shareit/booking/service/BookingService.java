package ru.practicum.shareit.booking.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BookingService {
    BookingResponseDto getById(long bookingId, long userId);

    @Transactional
    BookingResponseDto create(BookingCreateDto bookingDto, long userId);

    @Transactional
    void deleteById(long id);

    @Transactional
    BookingResponseDto approveBooking(boolean isApproved, long bookingId, long userId);

    List<BookingResponseDto> getUserBookingsByState(long userId, String state);

    List<BookingResponseDto> getBookingsByItemsOwner(long ownerId, String state);
}
