package ru.practicum.main.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventCanceledException extends RuntimeException {
    public EventCanceledException(String message) {
        super(message);
    }
}
