package ru.practicum.main.exception;

/**
 * @author Oleg Khilko
 */

public class NameExistException extends RuntimeException {
    public NameExistException(String message) {
        super(message);
    }
}
