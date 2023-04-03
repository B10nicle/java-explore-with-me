package ru.practicum.main.request.mapper;

import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.entity.Request;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestDto toRequestDto(Request request);

    List<RequestDto> toRequestDtos(List<Request> requests);
}
