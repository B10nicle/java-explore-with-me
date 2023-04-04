package ru.practicum.hit.service;

import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.dto.HitDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface HitService {
    void saveHit(HitDto hitDto);

    List<ViewStatsDto> getHits(LocalDateTime start,
                               LocalDateTime end,
                               List<String> uris,
                               boolean unique);
}
