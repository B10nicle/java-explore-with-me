package ru.practicum.main.event.error;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.main.error.entity.Error;
import ru.practicum.main.event.exception.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static ru.practicum.main.utils.Patterns.DATE_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.http.HttpStatus.*;
import static java.time.LocalDateTime.now;

/**
 * @author Oleg Khilko
 */

@RestControllerAdvice
public class EventErrorHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleEventNotPublishedException(final EventNotPublishedException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Conditions are wrong")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleEventPublishedException(final EventPublishedException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Conditions are wrong")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleEventCanceledException(final EventCanceledException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Conditions are wrong")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleEventWrongTimeException(final EventWrongTimeException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Wrong event time")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Error handleEventNotExistException(final EventNotExistException exception) {
        return Error.builder()
                .status(NOT_FOUND.getReasonPhrase().toUpperCase())
                .reason(("This event does not exist"))
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }
}
