package ru.practicum.server.hit.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.hit.repository.HitRepository;
import ru.practicum.server.hit.mapper.ViewStatsMapper;
import ru.practicum.server.hit.mapper.HitMapper;
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
        log.debug("App saved hit: " + hitDto.getApp());
        hitRepository.save(hitMapper.toEntity(hitDto));
    }

    @Override
    public List<ViewStatsDto> getHits(LocalDateTime start,
                                      LocalDateTime end,
                                      List<String> uris,
                                      boolean unique) {
        log.debug("Hits were successfully received");
        return unique
                ? viewStatsMapper.toEntities(hitRepository.getDistinctHits(start, end, uris))
                : viewStatsMapper.toEntities(hitRepository.getHits(start, end, uris));
    }
}
