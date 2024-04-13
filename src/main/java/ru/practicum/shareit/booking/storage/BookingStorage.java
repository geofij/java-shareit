package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingStorage {
    Booking get(long id);

    Booking add(Booking booking);

    Booking update(Booking booking);

    void delete(long id);
}
