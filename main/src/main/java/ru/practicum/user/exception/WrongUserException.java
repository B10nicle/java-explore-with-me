package ru.practicum.user.exception;

/**
 * @author Oleg Khilko
 */

public class WrongUserException extends RuntimeException {
    public WrongUserException(String message) {
        super(message);
    }
}
