package ru.practicum.evm.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventNotPublishedException extends RuntimeException {
    public EventNotPublishedException(String message) {
        super(message);
    }
}
