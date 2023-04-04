package ru.practicum.stats.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @NotBlank
    private String ip;

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    private String timestamp;
}
