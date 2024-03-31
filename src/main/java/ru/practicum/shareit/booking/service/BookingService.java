package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingService {
    Booking get(Long id);

    Booking add(Booking user);

    Booking update(Booking user);

    void delete(Long id);
}
