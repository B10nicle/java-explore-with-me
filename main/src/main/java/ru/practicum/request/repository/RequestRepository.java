package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.request.entity.Request;

import java.util.Optional;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT request FROM Request AS request " +
            "JOIN Event AS event ON request.event = event.id " +
            "WHERE request.event = :eventId AND event.initiator.id = :userId")
    List<Request> findAllByEventWithInitiator(@Param("userId") Long userId,
                                              @Param("eventId") Long eventId);

    Optional<Request> findByRequesterAndId(Long userId,
                                           Long requestId);

    Boolean existsByRequesterAndEvent(Long userId,
                                      Long eventId);

    List<Request> findAllByRequester(Long userId);

    List<Request> findAllByEvent(Long eventId);
}
