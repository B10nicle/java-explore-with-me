package ru.practicum.main.compilation.error;

import ru.practicum.main.compilation.exception.CompilationNotExistException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.main.error.entity.Error;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ru.practicum.main.utils.Patterns.DATE_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.LocalDateTime.now;

/**
 * @author Oleg Khilko
 */

@RestControllerAdvice
public class CompilationErrorHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Error handleCompilationNotExistException(final CompilationNotExistException exception) {
        return Error.builder()
                .status(NOT_FOUND.getReasonPhrase().toUpperCase())
                .reason(("This compilation does not exist"))
                .message(exception.getMessage())
                .timestamp(now().format(ofPattern(DATE_PATTERN)))
                .build();
    }
}
