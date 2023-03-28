package ru.practicum.stats.server.hit.controller;

import ru.practicum.stats.server.hit.service.EndpointHitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    public EndpointHitDto createEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        return endpointHitService.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getEndpointHits(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                              @RequestParam(defaultValue = "false") boolean unique,
                                              @RequestParam(required = false) List<String> uris) {
        return endpointHitService.getEndpointHits(start, end, uris, unique);
    }
}
