package ru.practicum.shareit.error;

public class BookingStartEndValidationException extends RuntimeException {
    public BookingStartEndValidationException(String message) {
        super(message);
    }
}
