package ru.practicum.request.controller;

import ru.practicum.request.service.RequestService;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import lombok.AllArgsConstructor;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(CREATED)
    public RequestDto saveRequest(@RequestParam Long eventId,
                                  @PathVariable Long userId) {
        return requestService.saveRequest(userId, eventId);
    }

    @GetMapping
    public List<RequestDto> getCurrentUserRequests(@PathVariable Long userId) {
        return requestService.getCurrentUserRequests(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long requestId,
                                    @PathVariable Long userId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
