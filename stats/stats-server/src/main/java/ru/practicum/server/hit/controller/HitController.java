package ru.practicum.server.hit.controller;

import ru.practicum.server.hit.service.HitService;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.dto.HitDto;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(CREATED)
    public void createHit(@RequestBody HitDto hitDto) {
        hitService.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam(value = "start") LocalDateTime start,
                                       @RequestParam(value = "end") LocalDateTime end,
                                       @RequestParam(required = false, defaultValue = "false") boolean unique,
                                       @RequestParam(required = false) List<String> uris) {
        return hitService.getHits(start, end, uris, unique);
    }
}
