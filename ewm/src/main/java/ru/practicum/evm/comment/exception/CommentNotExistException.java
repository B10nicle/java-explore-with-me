package ru.practicum.evm.comment.exception;

/**
 * @author Oleg Khilko
 */

public class CommentNotExistException extends RuntimeException {
    public CommentNotExistException(String message) {
        super(message);
    }
}
