package ru.practicum.main.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventPublishedException extends RuntimeException {
    public EventPublishedException(String message) {
        super(message);
    }
}
