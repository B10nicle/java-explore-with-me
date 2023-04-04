package ru.practicum.request.exception;

/**
 * @author Oleg Khilko
 */

public class RequestNotExistException extends RuntimeException {
    public RequestNotExistException(String message) {
        super(message);
    }
}
