package ru.practicum.hit.repository;

import ru.practicum.hit.entity.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface HitRepository {
    List<ViewStats> getStats(LocalDateTime start,
                             LocalDateTime end,
                             List<String> uris,
                             boolean unique);
}
