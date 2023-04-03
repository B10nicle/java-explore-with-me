package ru.practicum.stats.dto;

import lombok.*;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private Long id;
    private String ip;
    private String app;
    private String uri;
    private String timestamp;
}
