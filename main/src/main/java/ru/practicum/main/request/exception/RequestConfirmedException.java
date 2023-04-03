package ru.practicum.main.request.exception;

/**
 * @author Oleg Khilko
 */

public class RequestConfirmedException extends RuntimeException {
    public RequestConfirmedException(String message) {
        super(message);
    }
}
