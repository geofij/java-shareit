package ru.practicum.shareit.exception;

public class ItemNotBeBookedException extends RuntimeException {
    public ItemNotBeBookedException(String message) {
        super(message);
    }
}