package ru.practicum.main.category.error;

import ru.practicum.main.category.exception.CategoryNotEmptyException;
import ru.practicum.main.category.exception.CategoryNotExistException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class CategoryErrorHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public Error handleCategoryNotEmptyException(final CategoryNotEmptyException exception) {
        return Error.builder()
                .status(CONFLICT.getReasonPhrase().toUpperCase())
                .reason("Conditions are wrong")
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Error handleCategoryNotExistException(final CategoryNotExistException exception) {
        return Error.builder()
                .status(NOT_FOUND.getReasonPhrase().toUpperCase())
                .reason(("This category does not exist"))
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }
}
