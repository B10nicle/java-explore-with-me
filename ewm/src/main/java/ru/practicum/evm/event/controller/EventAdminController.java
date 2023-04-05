package ru.practicum.evm.event.controller;

import org.springframework.validation.annotation.Validated;
import ru.practicum.evm.event.dto.UpdateEventAdminDto;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.event.service.EventService;
import ru.practicum.evm.event.dto.LongEventDto;
import ru.practicum.evm.event.enums.EventState;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class EventAdminController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<LongEventDto> getEvents(@RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, defaultValue = "0") Integer from,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) EventState states,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) String rangeEnd) {
        return eventService.getEventsWithParamsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public LongEventDto updateEvent(@Valid @RequestBody UpdateEventAdminDto updateEventAdminDto,
                                    @PathVariable Long eventId) {
        return eventService.updateEvent(eventId, updateEventAdminDto);
    }
}
