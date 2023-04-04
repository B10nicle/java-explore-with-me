package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.event.enums.StateActionForUser;
import ru.practicum.event.entity.Location;
import ru.practicum.utils.Patterns;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserDto {

    @Size(min = 3, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    @Size(min = 3, max = 120)
    private String title;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateActionForUser stateAction;
}
