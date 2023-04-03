package ru.practicum.main.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventNotExistException extends RuntimeException {
    public EventNotExistException(String message) {
        super(message);
    }
}
