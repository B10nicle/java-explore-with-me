package ru.practicum.evm.compilation.exception;

/**
 * @author Oleg Khilko
 */

public class CompilationNotExistException extends RuntimeException {
    public CompilationNotExistException(String message) {
        super(message);
    }
}
