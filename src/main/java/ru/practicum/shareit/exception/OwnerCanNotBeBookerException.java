package ru.practicum.shareit.exception;

public class OwnerCanNotBeBookerException extends RuntimeException {
    public OwnerCanNotBeBookerException(String message) {
        super(message);
    }
}