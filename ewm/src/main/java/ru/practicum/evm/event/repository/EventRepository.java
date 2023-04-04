package ru.practicum.evm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import ru.practicum.evm.event.entity.Event;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    Page<Event> findAllByInitiatorId(Long userId, Pageable page);

    Optional<Event> findByIdAndPublishedOnIsNotNull(Long id);

    List<Event> findAllByIdIn(List<Long> eventIds);

    Boolean existsByCategoryId(Long id);
}
