package ru.practicum.main.category.exception;

/**
 * @author Oleg Khilko
 */

public class CategoryNotEmptyException extends RuntimeException {
    public CategoryNotEmptyException(String message) {
        super(message);
    }
}
