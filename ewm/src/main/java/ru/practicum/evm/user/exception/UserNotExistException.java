package ru.practicum.evm.user.exception;

/**
 * @author Oleg Khilko
 */

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
