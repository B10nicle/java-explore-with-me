package ru.practicum.main.user.error;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.main.user.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.main.user.exception.NameExistException;
import ru.practicum.main.user.exception.WrongUserException;
import ru.practicum.main.error.entity.Error;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ru.practicum.main.utils.Patterns.DATE_PATTERN;
import static org.springframework.http.HttpStatus.CONFLICT;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.LocalDateTime.now;

/**
 * @author Oleg Khilko
 */

@RestControllerAdvice
public class UserErrorHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleNameExistException(final NameExistException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("This username is already exist")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleWrongUserException(final WrongUserException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Wrong user")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Error handleUserNotExistException(final UserNotExistException exception) {
        return Error.builder()
                .status(NOT_FOUND.getReasonPhrase().toUpperCase())
                .reason("This user does not exist")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }
}
