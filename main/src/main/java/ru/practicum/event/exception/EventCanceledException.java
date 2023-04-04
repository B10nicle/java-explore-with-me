package ru.practicum.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventCanceledException extends RuntimeException {
    public EventCanceledException(String message) {
        super(message);
    }
}
