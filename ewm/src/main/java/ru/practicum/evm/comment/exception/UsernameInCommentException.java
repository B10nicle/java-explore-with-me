package ru.practicum.evm.comment.exception;

/**
 * @author Oleg Khilko
 */

public class UsernameInCommentException extends RuntimeException {
    public UsernameInCommentException(String message) {
        super(message);
    }
}
