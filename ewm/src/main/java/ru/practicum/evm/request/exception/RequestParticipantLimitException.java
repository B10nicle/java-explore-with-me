package ru.practicum.evm.request.exception;

/**
 * @author Oleg Khilko
 */

public class RequestParticipantLimitException extends RuntimeException {
    public RequestParticipantLimitException(String message) {
        super(message);
    }
}
