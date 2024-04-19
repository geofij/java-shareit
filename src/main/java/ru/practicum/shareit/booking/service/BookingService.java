package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

public interface BookingService {
    Booking get(long id);

    Booking add(Booking booking);

    Booking update(Booking booking);

    void delete(long id);
}
