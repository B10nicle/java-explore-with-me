package ru.practicum.hit.mapper;

import ru.practicum.hit.utils.Patterns;
import ru.practicum.hit.entity.Hit;
import ru.practicum.stats.dto.HitDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = Patterns.DATE_PATTERN)
    Hit toEntity(HitDto hitDto);
}
