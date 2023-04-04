package ru.practicum.user.exception;

/**
 * @author Oleg Khilko
 */

public class NameExistException extends RuntimeException {
    public NameExistException(String message) {
        super(message);
    }
}
