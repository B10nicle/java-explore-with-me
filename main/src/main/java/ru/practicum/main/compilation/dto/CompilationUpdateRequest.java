package ru.practicum.main.compilation.dto;

import lombok.*;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationUpdateRequest {
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
