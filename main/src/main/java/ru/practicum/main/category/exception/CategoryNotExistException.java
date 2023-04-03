package ru.practicum.main.category.exception;

/**
 * @author Oleg Khilko
 */

public class CategoryNotExistException extends RuntimeException {
    public CategoryNotExistException(String message) {
        super(message);
    }
}
