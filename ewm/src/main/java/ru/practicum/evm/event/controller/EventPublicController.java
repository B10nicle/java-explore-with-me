package ru.practicum.evm.event.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.event.service.EventService;
import ru.practicum.evm.event.dto.LongEventDto;
import ru.practicum.evm.event.enums.SortValue;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventPublicController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public LongEventDto getEvent(@PathVariable Long id, HttpServletRequest request) {
        return eventService.getEvent(id, request);
    }

    @GetMapping
    public List<LongEventDto> getEventsWithParamsByUser(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                        @RequestParam(required = false) List<Long> categories,
                                                        @RequestParam(required = false) String rangeStart,
                                                        @RequestParam(required = false) String rangeEnd,
                                                        @RequestParam(required = false) SortValue sort,
                                                        @RequestParam(required = false) Boolean paid,
                                                        @RequestParam(required = false) String text,
                                                        HttpServletRequest request) {
        return eventService.getEventsWithParamsByUser(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }
}
