package ru.practicum.main.exception;

/**
 * @author Oleg Khilko
 */

public class CategoryNotExistException extends RuntimeException {
    public CategoryNotExistException(String message) {
        super(message);
    }
}
