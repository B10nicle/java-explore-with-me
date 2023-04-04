package ru.practicum.event.controller;

import org.springframework.validation.annotation.Validated;
import ru.practicum.request.dto.RequestUpdateResult;
import ru.practicum.request.service.RequestService;
import ru.practicum.request.dto.RequestUpdateDto;
import ru.practicum.event.dto.UpdateEventUserDto;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.event.dto.SavedEventDto;
import ru.practicum.event.dto.ShortEventDto;
import ru.practicum.event.dto.LongEventDto;
import ru.practicum.request.dto.RequestDto;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class EventPrivateController {
    private final RequestService requestService;
    private final EventService eventService;

    @ResponseStatus(CREATED)
    @PostMapping("/events")
    public LongEventDto saveEvent(@Valid @RequestBody SavedEventDto savedEventDto,
                                  @PathVariable Long userId) {
        return eventService.saveEvent(userId, savedEventDto);
    }

    @GetMapping("/events")
    public List<ShortEventDto> getEventsByUser(@RequestParam(required = false, defaultValue = "10") Integer size,
                                               @RequestParam(required = false, defaultValue = "0") Integer from,
                                               @PathVariable Long userId) {
        return eventService.getEvents(userId, from, size);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<RequestDto> getRequestsByOwnerOfEvent(@PathVariable Long eventId,
                                                      @PathVariable Long userId) {
        return requestService.getRequestsByOwnerOfEvent(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    public RequestUpdateResult updateRequests(@RequestBody RequestUpdateDto requestUpdateDto,
                                              @PathVariable Long eventId,
                                              @PathVariable Long userId) {
        return requestService.updateRequests(userId, eventId, requestUpdateDto);
    }

    @PatchMapping("/events/{eventId}")
    public LongEventDto updateEventByUser(@Valid @RequestBody UpdateEventUserDto updateEventUserDto,
                                          @PathVariable Long eventId,
                                          @PathVariable Long userId) {
        return eventService.updateEventByUser(userId, eventId, updateEventUserDto);
    }

    @GetMapping("/events/{eventId}")
    public LongEventDto getEventByUser(@PathVariable Long eventId,
                                       @PathVariable Long userId) {
        return eventService.getEventByUser(userId, eventId);
    }
}
