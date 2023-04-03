package ru.practicum.main.compilation.exception;

/**
 * @author Oleg Khilko
 */

public class CompilationNotExistException extends RuntimeException {
    public CompilationNotExistException(String message) {
        super(message);
    }
}
