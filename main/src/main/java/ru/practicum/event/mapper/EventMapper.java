package ru.practicum.event.mapper;

import ru.practicum.event.dto.ShortEventDto;
import ru.practicum.event.dto.SavedEventDto;
import ru.practicum.event.dto.LongEventDto;
import ru.practicum.event.entity.Event;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "category", target = "category.id")
    Event toEvent(SavedEventDto savedEventDto);

    LongEventDto toLongEventDto(Event event);

    List<ShortEventDto> toShortEventDtos(List<Event> events);

    List<LongEventDto> toLongEventDtos(List<Event> events);
}
