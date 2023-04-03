package ru.practicum.server.hit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.server.hit.entity.ViewStats;
import ru.practicum.server.hit.entity.Hit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = ""
            + "SELECT NEW ru.practicum.server.hit.entity.ViewStats(hit.uri, hit.app, COUNT(hit.ip)) "
            + "FROM Hit hit "
            + "WHERE hit.uri IN ?3 AND hit.timestamp BETWEEN ?1 AND ?2 "
            + "GROUP BY hit.uri, hit.app "
            + "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> getHits(LocalDateTime start,
                            LocalDateTime end,
                            List<String> uris);

    @Query(value = ""
            + "SELECT NEW ru.practicum.server.hit.entity.ViewStats(hit.uri, hit.app, COUNT(DISTINCT hit.ip)) "
            + "FROM Hit hit "
            + "WHERE hit.uri IN ?3 AND hit.timestamp BETWEEN ?1 AND ?2 "
            + "GROUP BY hit.uri, hit.app "
            + "ORDER BY COUNT(DISTINCT hit.ip) DESC")
    List<ViewStats> getDistinctHits(LocalDateTime start,
                                    LocalDateTime end,
                                    List<String> uris);
}
