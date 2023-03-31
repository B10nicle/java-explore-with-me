package ru.practicum.server.hit.mapper;

import ru.practicum.server.hit.model.ViewStats;
import ru.practicum.stats.dto.ViewStatsDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    List<ViewStatsDto> toEntities(List<ViewStats> viewStats);
}
