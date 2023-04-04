package ru.practicum.error.controller;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.error.entity.Error;
import ru.practicum.utils.Patterns;

import static org.springframework.http.HttpStatus.*;
import static java.time.format.DateTimeFormatter.*;
import static java.time.LocalDateTime.*;

/**
 * @author Oleg Khilko
 */

@RestControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Error handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        return Error.builder()
                .status(BAD_REQUEST.getReasonPhrase().toUpperCase())
                .reason("Incorrectly made request")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(Patterns.DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return Error.builder()
                .status(BAD_REQUEST.getReasonPhrase().toUpperCase())
                .reason("Incorrectly made request")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(Patterns.DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Error handleEmptyResultDataAccessException(final EmptyResultDataAccessException exception) {
        return Error.builder()
                .status(NOT_FOUND.getReasonPhrase().toUpperCase())
                .reason("Empty result data")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(Patterns.DATE_PATTERN)))
                .build();
    }


}
