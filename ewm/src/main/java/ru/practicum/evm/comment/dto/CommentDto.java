package ru.practicum.evm.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static ru.practicum.evm.utils.Patterns.*;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String userName;
    private Long eventId;

    @NotBlank
    @Size(min = 5, max = 500)
    private String text;

    @JsonFormat(shape = STRING, pattern = DATE_PATTERN)
    private LocalDateTime created;
}
