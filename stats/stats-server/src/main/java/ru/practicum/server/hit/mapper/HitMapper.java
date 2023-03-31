package ru.practicum.server.hit.mapper;

import ru.practicum.server.hit.model.EndpointHit;
import ru.practicum.stats.dto.HitDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit toEntity(HitDto hitDto);
}
