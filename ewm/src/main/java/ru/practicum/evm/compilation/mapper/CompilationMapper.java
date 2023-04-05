package ru.practicum.evm.compilation.mapper;

import ru.practicum.evm.compilation.dto.CompilationDto;
import ru.practicum.evm.compilation.entity.Compilation;
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
