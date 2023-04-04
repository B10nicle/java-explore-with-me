package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.event.entity.Location;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.enums.EventState;
import lombok.*;
import ru.practicum.utils.Patterns;

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
public class LongEventDto {
    private Long id;

    @Size(max = 2000)
    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private String createdOn;

    @Size(max = 7000)
    private String description;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private LocalDateTime eventDate;

    private ShortEventDto initiator;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventState state;

    @Size(max = 120)
    private String title;

    private Long views;
}
