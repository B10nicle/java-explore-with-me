package ru.practicum.evm.event.exception;

/**
 * @author Oleg Khilko
 */

public class EventWrongTimeException extends RuntimeException {
    public EventWrongTimeException(String message) {
        super(message);
    }
}
