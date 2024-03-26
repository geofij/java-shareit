package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingStorage {
    Booking get(Booking user);

    Booking add(Booking user);

    Booking update(Booking user);

    void delete(Booking user);
}
