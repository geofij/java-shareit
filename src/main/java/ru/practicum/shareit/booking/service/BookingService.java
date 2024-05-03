package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking getById(long bookingId, long userId);

    Booking create(BookingCreateDto bookingDto, long userId);

    void deleteById(long id);

    Booking approveBooking(boolean isApproved, long bookingId, long userId);

    List<Booking> getUserBookingsByState(long userId, String state);

    List<Booking> getBookingsByItemsOwner(long ownerId, String state);
}
