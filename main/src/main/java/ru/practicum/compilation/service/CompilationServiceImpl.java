package ru.practicum.compilation.service;

import ru.practicum.compilation.exception.CompilationNotExistException;
import ru.practicum.compilation.repository.CompilationRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationUpdateRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.dto.SavedCompilationDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.entity.Compilation;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.Predicate;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public CompilationDto saveCompilation(SavedCompilationDto savedCompilationDto) {
        var events = eventRepository.findAllByIdIn(savedCompilationDto.getEvents());

        var compilation = Compilation.builder()
                .pinned(savedCompilationDto.getPinned())
                .title(savedCompilationDto.getTitle())
                .events(new HashSet<>(events))
                .build();

        var saved = compilationRepository.save(compilation);
        return compilationMapper.mapToCompilationDto(saved);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId,
                                            CompilationUpdateRequest compilationUpdateRequest) {
        var old = compilationRepository.findById(compId).orElseThrow(
                () -> new CompilationNotExistException("Compilation does not exist"));
        var eventsIds = compilationUpdateRequest.getEvents();
        if (eventsIds != null) {
            var events = eventRepository.findAllByIdIn(compilationUpdateRequest.getEvents());
            old.setEvents(new HashSet<>(events));
        }
        if (compilationUpdateRequest.getPinned() != null)
            old.setPinned(compilationUpdateRequest.getPinned());
        if (compilationUpdateRequest.getTitle() != null)
            old.setTitle(compilationUpdateRequest.getTitle());

        var updated = compilationRepository.save(old);
        return compilationMapper.mapToCompilationDto(updated);
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        var compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new CompilationNotExistException("Compilation does not exist"));
        return compilationMapper.mapToCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned,
                                                Integer from,
                                                Integer size) {
        Predicate isPinned;
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Compilation.class);
        var compilationRoot = query.from(Compilation.class);
        var criteria = criteriaBuilder.conjunction();

        if (pinned != null) {
            if (pinned)
                isPinned = criteriaBuilder.isTrue(compilationRoot.get("pinned"));
            else
                isPinned = criteriaBuilder.isFalse(compilationRoot.get("pinned"));

            criteria = criteriaBuilder.and(criteria, isPinned);
        }
        query.select(compilationRoot).where(criteria);
        var compilations = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        return compilationMapper.mapToCompilationDtos(compilations);
    }
}
