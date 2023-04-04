package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.ShortUserDto;
import ru.practicum.utils.Patterns;
import lombok.*;

import javax.validation.constraints.Size;
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
public class ShortEventDto {
    private Long id;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
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
