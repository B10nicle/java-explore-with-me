package ru.practicum.evm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.evm.category.dto.CategoryDto;
import ru.practicum.evm.event.enums.EventState;
import ru.practicum.evm.event.entity.Location;

import lombok.*;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LongEventDto {
    private Long id;

    @Size(max = 2000)
    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(shape = STRING, pattern = DATE_PATTERN)
    private String createdOn;

    @Size(max = 7000)
    private String description;

    @JsonFormat(shape = STRING, pattern = DATE_PATTERN)
    private LocalDateTime eventDate;

    private ShortEventDto initiator;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    @JsonFormat(shape = STRING, pattern = DATE_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventState state;

    @Size(max = 120)
    private String title;

    private Long views;
}
