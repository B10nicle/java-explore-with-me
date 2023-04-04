package ru.practicum.compilation.controller;

import ru.practicum.compilation.dto.CompilationUpdateRequest;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.compilation.dto.SavedCompilationDto;
import org.springframework.validation.annotation.Validated;
import ru.practicum.compilation.dto.CompilationDto;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class CompilationAdminController {
    private final CompilationService compilationService;

    @ResponseStatus(CREATED)
    @PostMapping("/compilations")
    public CompilationDto saveCompilation(@Valid @RequestBody SavedCompilationDto savedCompilationDto) {
        return compilationService.saveCompilation(savedCompilationDto);
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto updateCompilation(@Valid @RequestBody CompilationUpdateRequest compilationUpdateRequest,
                                           @PathVariable Long compId) {
        return compilationService.updateCompilation(compId, compilationUpdateRequest);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }
}
