package ru.practicum.main.compilation.mapper;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.entity.Compilation;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationDto mapToCompilationDto(Compilation compilation);

    List<CompilationDto> mapToCompilationDtos(List<Compilation> compilations);
}
