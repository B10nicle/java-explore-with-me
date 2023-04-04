package ru.practicum.request.mapper;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.entity.Request;
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
