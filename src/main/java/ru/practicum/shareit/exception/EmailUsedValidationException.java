package ru.practicum.shareit.exception;

public class EmailUsedValidationException extends RuntimeException {
    public EmailUsedValidationException(String message) {
        super(message);
    }
}
