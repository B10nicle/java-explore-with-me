package ru.practicum.event.service;

import ru.practicum.category.exception.CategoryNotExistException;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.exception.EventPublishedException;
import ru.practicum.event.exception.EventWrongTimeException;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.exception.EventNotExistException;
import ru.practicum.event.exception.EventCanceledException;
import ru.practicum.user.exception.UserNotExistException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.event.enums.EventState;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.event.enums.SortValue;
import ru.practicum.event.entity.Event;
import ru.practicum.stats.dto.HitDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.event.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.criteria.Predicate;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

import static ru.practicum.event.enums.StateActionForAdmin.*;
import static ru.practicum.event.enums.StateActionForUser.*;
import static org.springframework.data.domain.PageRequest.*;
import static ru.practicum.event.enums.EventState.*;
import static java.time.format.DateTimeFormatter.*;
import static ru.practicum.event.enums.SortValue.*;
import static ru.practicum.utils.Patterns.*;
import static java.util.stream.Collectors.*;
import static java.time.LocalDateTime.*;
import static java.util.Comparator.*;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final EventMapper eventMapper;
    private final StatsClient statsClient;

    @Override
    @Transactional
    public LongEventDto saveEvent(Long userId,
                                  SavedEventDto savedEventDto) {
        var category = categoryRepository.findById(savedEventDto.getCategory()).orElseThrow(
                () -> new CategoryNotExistException("This category does not exist"));
        var eventDate = savedEventDto.getEventDate();

        if (eventDate.isBefore(now().plusHours(2)))
            throw new EventWrongTimeException("EventDate should be in future");

        var event = eventMapper.toEvent(savedEventDto);
        event.setCategory(category);

        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException("User#" + userId + " does not exist"));

        event.setInitiator(user);

        return eventMapper.toLongEventDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public LongEventDto updateEvent(Long eventId,
                                    UpdateEventAdminDto updateEventAdminDto) {
        var event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + " does not exist"));

        if (updateEventAdminDto == null)
            return eventMapper.toLongEventDto(event);

        if (updateEventAdminDto.getAnnotation() != null)
            event.setAnnotation(updateEventAdminDto.getAnnotation());

        if (updateEventAdminDto.getCategory() != null) {
            var category = categoryRepository.findById(updateEventAdminDto.getCategory()).orElseThrow(
                    () -> new CategoryNotExistException("This category does not exist"));
            event.setCategory(category);
        }
        if (updateEventAdminDto.getDescription() != null)
            event.setDescription(updateEventAdminDto.getDescription());

        if (updateEventAdminDto.getLocation() != null)
            event.setLocation(updateEventAdminDto.getLocation());

        if (updateEventAdminDto.getPaid() != null)
            event.setPaid(updateEventAdminDto.getPaid());

        if (updateEventAdminDto.getParticipantLimit() != null)
            event.setParticipantLimit(updateEventAdminDto.getParticipantLimit().intValue());

        if (updateEventAdminDto.getRequestModeration() != null)
            event.setRequestModeration(updateEventAdminDto.getRequestModeration());

        if (updateEventAdminDto.getTitle() != null)
            event.setTitle(updateEventAdminDto.getTitle());

        if (updateEventAdminDto.getStateAction() != null) {
            if (PUBLISH_EVENT.equals(updateEventAdminDto.getStateAction())) {
                if (event.getPublishedOn() != null)
                    throw new EventPublishedException("Event has been published");
                if (CANCELED.equals(event.getState()))
                    throw new EventCanceledException("Event has been canceled");
                event.setState(PUBLISHED);
                event.setPublishedOn(now());
            } else if (REJECT_EVENT.equals(updateEventAdminDto.getStateAction())) {
                if (event.getPublishedOn() != null)
                    throw new EventPublishedException("Event has been published");
                event.setState(CANCELED);
            }
        }
        if (updateEventAdminDto.getEventDate() != null) {
            var eventTime = updateEventAdminDto.getEventDate();
            if (eventTime.isBefore(now())
                    || eventTime.isBefore(event.getPublishedOn().plusHours(1)))
                throw new EventWrongTimeException("Wrong time");

            event.setEventDate(updateEventAdminDto.getEventDate());
        }
        var saved = eventRepository.save(event);

        return eventMapper.toLongEventDto(saved);
    }

    @Override
    @Transactional
    public LongEventDto updateEventByUser(Long userId,
                                          Long eventId,
                                          UpdateEventUserDto updateEventUserDto) {
        var event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + " does not exist"));

        if (event.getPublishedOn() != null)
            throw new EventPublishedException("Event has been published");

        if (updateEventUserDto == null)
            return eventMapper.toLongEventDto(event);

        if (updateEventUserDto.getAnnotation() != null)
            event.setAnnotation(updateEventUserDto.getAnnotation());

        if (updateEventUserDto.getCategory() != null) {
            var category = categoryRepository.findById(updateEventUserDto.getCategory()).orElseThrow(
                    () -> new CategoryNotExistException("This category does not exist"));
            event.setCategory(category);
        }
        if (updateEventUserDto.getDescription() != null)
            event.setDescription(updateEventUserDto.getDescription());

        if (updateEventUserDto.getEventDate() != null) {
            var eventTime = updateEventUserDto.getEventDate();
            if (eventTime.isBefore(now().plusHours(2)))
                throw new EventWrongTimeException("Wrong time");
            event.setEventDate(updateEventUserDto.getEventDate());
        }
        if (updateEventUserDto.getLocation() != null)
            event.setLocation(updateEventUserDto.getLocation());

        if (updateEventUserDto.getPaid() != null)
            event.setPaid(updateEventUserDto.getPaid());

        if (updateEventUserDto.getParticipantLimit() != null)
            event.setParticipantLimit(updateEventUserDto.getParticipantLimit().intValue());

        if (updateEventUserDto.getRequestModeration() != null)
            event.setRequestModeration(updateEventUserDto.getRequestModeration());

        if (updateEventUserDto.getTitle() != null)
            event.setTitle(updateEventUserDto.getTitle());

        if (updateEventUserDto.getStateAction() != null) {
            if (SEND_TO_REVIEW.equals(updateEventUserDto.getStateAction()))
                event.setState(PENDING);
            else
                event.setState(CANCELED);
        }
        return eventMapper.toLongEventDto(eventRepository.save(event));
    }

    @Override
    public List<ShortEventDto> getEvents(Long userId,
                                         Integer from,
                                         Integer size) {
        var events = eventRepository.findAllByInitiatorId(userId, of(from / size, size)).toList();
        return eventMapper.toShortEventDtos(events);
    }

    @Override
    public LongEventDto getEventByUser(Long userId,
                                       Long eventId) {
        return eventMapper.toLongEventDto(eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + " does not exist")));
    }

    @Override
    public LongEventDto getEvent(Long id,
                                 HttpServletRequest request) {
        var event = eventRepository.findByIdAndPublishedOnIsNotNull(id).orElseThrow(
                () -> new EventNotExistException("Event#" + id + " does not exist"));
        addView(event);
        sendStats(event, request);
        return eventMapper.toLongEventDto(event);
    }

    @Override
    public List<LongEventDto> getEventsWithParamsByAdmin(List<Long> users,
                                                         EventState states,
                                                         List<Long> categories,
                                                         String rangeStart,
                                                         String rangeEnd,
                                                         Integer from,
                                                         Integer size) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Event.class);
        var root = query.from(Event.class);
        var criteria = builder.conjunction();

        var start = rangeStart == null ? null : parse(rangeStart, ofPattern(DATE_PATTERN));
        var end = rangeEnd == null ? null : parse(rangeEnd, ofPattern(DATE_PATTERN));

        if (rangeStart != null)
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), start));

        if (rangeEnd != null)
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), end));

        if (categories != null && categories.size() > 0)
            criteria = builder.and(criteria, root.get("category").in(categories));

        if (users != null && users.size() > 0)
            criteria = builder.and(criteria, root.get("initiator").in(users));

        if (states != null)
            criteria = builder.and(criteria, root.get("state").in(states));

        query.select(root).where(criteria);

        var events = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (events.size() == 0) return new ArrayList<>();

        return eventMapper.toLongEventDtos(events);
    }

    @Override
    public List<LongEventDto> getEventsWithParamsByUser(String text,
                                                        List<Long> categories,
                                                        Boolean paid,
                                                        String rangeStart,
                                                        String rangeEnd,
                                                        Boolean available,
                                                        SortValue sort,
                                                        Integer from,
                                                        Integer size,
                                                        HttpServletRequest request) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Event.class);
        var root = query.from(Event.class);
        var criteria = builder.conjunction();

        var start = rangeStart == null ? null : parse(rangeStart, ofPattern(DATE_PATTERN));
        var end = rangeEnd == null ? null : parse(rangeEnd, ofPattern(DATE_PATTERN));

        if (text != null) {
            criteria = builder.and(criteria, builder.or(
                    builder.like(
                            builder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%"),
                    builder.like(
                            builder.lower(root.get("description")), "%" + text.toLowerCase() + "%")));
        }

        if (categories != null && categories.size() > 0)
            criteria = builder.and(criteria, root.get("category").in(categories));

        if (paid != null) {
            Predicate predicate;
            if (paid) predicate = builder.isTrue(root.get("paid"));
            else predicate = builder.isFalse(root.get("paid"));
            criteria = builder.and(criteria, predicate);
        }

        if (rangeEnd != null)
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), end));

        if (rangeStart != null)
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), start));

        query.select(root).where(criteria).orderBy(builder.asc(root.get("eventDate")));

        var events = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (available)
            events = events.stream()
                    .filter((event -> event.getConfirmedRequests() < (long) event.getParticipantLimit()))
                    .collect(toList());

        if (sort != null) {
            if (EVENT_DATE.equals(sort))
                events = events.stream()
                        .sorted(comparing(Event::getEventDate))
                        .collect(toList());
            else
                events = events.stream()
                        .sorted(comparing(Event::getViews))
                        .collect(toList());
        }
        if (events.size() == 0) return new ArrayList<>();

        sendStats(events, request);
        return eventMapper.toLongEventDtos(events);
    }

    private List<ViewStatsDto> getStats(String start,
                                        String end,
                                        List<String> uris) {
        return statsClient.getStats(start, end, uris, false);
    }

    private void addView(Event event) {
        var start = event.getCreatedOn().format(ofPattern(DATE_PATTERN));
        var end = now().format(ofPattern(DATE_PATTERN));
        var uris = List.of("/events/" + event.getId());
        var stats = getStats(start, end, uris);

        if (stats.size() == 1)
            event.setViews(stats.get(0).getHits());
        else
            event.setViews(0L);
    }

    private void sendStats(Event event,
                           HttpServletRequest request) {
        var now = now();
        var requestDto = HitDto.builder()
                .ip(request.getRemoteAddr())
                .app("main")
                .uri("/events")
                .timestamp(now.format(ofPattern(DATE_PATTERN)))
                .build();

        statsClient.addStats(requestDto);
        sendStatsForTheEvent(
                event.getId(),
                request.getRemoteAddr(),
                now
        );
    }

    private void sendStats(List<Event> events,
                           HttpServletRequest request) {
        var now = now();
        var requestDto = HitDto.builder()
                .ip(request.getRemoteAddr())
                .app("main")
                .uri("/events")
                .timestamp(now.format(ofPattern(DATE_PATTERN)))
                .build();

        statsClient.addStats(requestDto);
        sendStatsForEveryEvent(
                events,
                request.getRemoteAddr(),
                now()
        );
    }

    private void sendStatsForTheEvent(Long eventId,
                                      String remoteAddress,
                                      LocalDateTime now) {
        var requestDto = HitDto.builder()
                .ip(remoteAddress)
                .app("main")
                .uri("/events/" + eventId)
                .timestamp(now.format(ofPattern(DATE_PATTERN)))
                .build();

        statsClient.addStats(requestDto);
    }

    private void sendStatsForEveryEvent(List<Event> events,
                                        String remoteAddress,
                                        LocalDateTime now) {
        for (var event : events) {
            var requestDto = HitDto.builder()
                    .ip(remoteAddress)
                    .app("main")
                    .uri("/events/" + event.getId())
                    .timestamp(now.format(ofPattern(DATE_PATTERN)))
                    .build();

            statsClient.addStats(requestDto);
        }
    }
}
