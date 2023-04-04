package ru.practicum.user.exception;

/**
 * @author Oleg Khilko
 */

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
