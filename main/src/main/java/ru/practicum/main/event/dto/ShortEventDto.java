package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.ShortUserDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static ru.practicum.main.utils.Patterns.*;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortEventDto {
    private Long id;

    @JsonFormat(shape = STRING, pattern = DATE_PATTERN)
    private LocalDateTime eventDate;

    private ShortUserDto initiator;

    private Long confirmedRequests;

    private CategoryDto category;

    @Size(max = 2000)
    private String annotation;

    private Boolean paid;

    @Size(max = 120)
    private String title;

    private Long views;
}
