package ru.practicum.evm.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedCompilationDto {
    @NotBlank
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
