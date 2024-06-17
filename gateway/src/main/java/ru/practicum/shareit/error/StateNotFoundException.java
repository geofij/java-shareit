package ru.practicum.shareit.error;

public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException(String message) {
        super(message);
    }

}
