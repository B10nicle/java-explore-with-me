package ru.practicum.evm.request.exception;

/**
 * @author Oleg Khilko
 */

public class RequestConfirmedException extends RuntimeException {
    public RequestConfirmedException(String message) {
        super(message);
    }
}
