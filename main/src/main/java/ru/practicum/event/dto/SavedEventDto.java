package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.event.entity.Location;
import ru.practicum.utils.Patterns;
import lombok.*;

import javax.validation.constraints.NotNull;
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
public class SavedEventDto {

    @NotNull
    @Size(min = 3, max = 2000)
    private String annotation;

    @NotNull
    @Size(min = 3, max = 120)
    private String title;

    @NotNull
    private Long category;

    @NotNull
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @JsonFormat(shape = STRING, pattern = Patterns.DATE_PATTERN)
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private boolean paid;

    private int participantLimit;

    private Boolean requestModeration;
}