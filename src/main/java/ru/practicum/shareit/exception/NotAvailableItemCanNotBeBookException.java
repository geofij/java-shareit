package ru.practicum.shareit.exception;

public class NotAvailableItemCanNotBeBookException extends RuntimeException {
    public NotAvailableItemCanNotBeBookException(String message) {
        super(message);
    }
}