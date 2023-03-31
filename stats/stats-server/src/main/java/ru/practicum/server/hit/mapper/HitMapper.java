package ru.practicum.server.hit.mapper;

import ru.practicum.server.hit.entity.Hit;
import ru.practicum.stats.dto.HitDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import static ru.practicum.server.hit.utils.Patterns.*;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = DATE_PATTERN)
    Hit toEntity(HitDto hitDto);
}
