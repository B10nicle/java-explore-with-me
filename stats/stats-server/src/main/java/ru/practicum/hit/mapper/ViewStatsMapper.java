package ru.practicum.hit.mapper;

import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.hit.entity.ViewStats;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    ViewStatsDto toDto(ViewStats viewStats);
}
