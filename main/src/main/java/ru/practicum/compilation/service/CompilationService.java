package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationUpdateRequest;
import ru.practicum.compilation.dto.SavedCompilationDto;
import ru.practicum.compilation.dto.CompilationDto;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface CompilationService {
    CompilationDto saveCompilation(SavedCompilationDto savedCompilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId,
                                     CompilationUpdateRequest compilationUpdateRequest);

    CompilationDto getCompilation(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned,
                                         Integer from,
                                         Integer size);
}
