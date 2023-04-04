package ru.practicum.evm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.evm.utils.Patterns;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private Long id;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private LocalDateTime created;

    private Long requester;
    private String status;
    private Long event;
}
