package ru.practicum.evm.request.exception;

/**
 * @author Oleg Khilko
 */

public class RequestExistException extends RuntimeException {
    public RequestExistException(String message) {
        super(message);
    }
}
