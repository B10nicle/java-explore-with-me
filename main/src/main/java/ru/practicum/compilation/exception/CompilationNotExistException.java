package ru.practicum.compilation.exception;

/**
 * @author Oleg Khilko
 */

public class CompilationNotExistException extends RuntimeException {
    public CompilationNotExistException(String message) {
        super(message);
    }
}
