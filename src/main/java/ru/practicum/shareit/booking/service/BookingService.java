package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingService {
    Booking get(Long id);

    Booking add(Booking booking);

    Booking update(Booking booking);

    void delete(Long id);
}
