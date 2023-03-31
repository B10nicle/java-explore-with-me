package ru.practicum.main.user.exception;

/**
 * @author Oleg Khilko
 */

public class NameAlreadyExistException extends RuntimeException {
    public NameAlreadyExistException(String message) {
        super(message);
    }
}
