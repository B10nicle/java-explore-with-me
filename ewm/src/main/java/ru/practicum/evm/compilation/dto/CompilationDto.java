package ru.practicum.evm.compilation.dto;

import ru.practicum.evm.event.dto.ShortEventDto;
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
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<ShortEventDto> events;
}
