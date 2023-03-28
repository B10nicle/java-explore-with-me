package ru.practicum.stats.server.hit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.server.hit.model.EndpointHit;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.server.hit.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {
    @Query(value = ""
            + "SELECT NEW ru.practicum.stats.server.hit.model.ViewStats(hit.uri, hit.app, COUNT(hit.ip)) "
            + "FROM EndpointHit hit "
            + "WHERE hit.uri IN ?3 AND hit.timestamp BETWEEN ?1 AND ?2 "
            + "GROUP BY hit.uri, hit.app "
            + "ORDER BY COUNT(hit.ip) DESC")
    List<ViewStats> getEndpointHits(LocalDateTime start,
                                    LocalDateTime end,
                                    List<String> uris);

    @Query(value = ""
            + "SELECT NEW ru.practicum.stats.server.hit.model.ViewStats(hit.uri, hit.app, COUNT(DISTINCT hit.ip)) "
            + "FROM EndpointHit hit "
            + "WHERE hit.uri IN ?3 AND hit.timestamp BETWEEN ?1 AND ?2 "
            + "GROUP BY hit.uri, hit.app "
            + "ORDER BY COUNT(DISTINCT hit.ip) DESC")
    List<ViewStats> getDistinctEndpointHits(LocalDateTime start,
                                            LocalDateTime end,
                                            List<String> uris);
}
