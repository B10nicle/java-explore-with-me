package ru.practicum.hit.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.hit.repository.HitRepository;
import ru.practicum.hit.mapper.ViewStatsMapper;
import ru.practicum.hit.mapper.HitMapper;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.dto.HitDto;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {
    private final ViewStatsMapper viewStatsMapper;
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    @Transactional
    public void saveHit(HitDto hitDto) {
        hitRepository.save(hitMapper.toEntity(hitDto));
    }

    @Override
    public List<ViewStatsDto> getHits(LocalDateTime start,
                                      LocalDateTime end,
                                      List<String> uris,
                                      boolean unique) {
        return unique
                ? viewStatsMapper.toEntities(hitRepository.getDistinctHits(start, end, uris))
                : viewStatsMapper.toEntities(hitRepository.getHits(start, end, uris));
    }
}
