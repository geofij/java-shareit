package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingStorage {
    Booking get(Long id);

    Booking add(Booking user);

    Booking update(Booking user);

    void delete(Long id);
}
