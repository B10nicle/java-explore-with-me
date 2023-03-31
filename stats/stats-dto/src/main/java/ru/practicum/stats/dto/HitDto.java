package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Oleg Khilko
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private Long id;
    private String ip;
    private String app;
    private String uri;
    private LocalDateTime timestamp;
}
