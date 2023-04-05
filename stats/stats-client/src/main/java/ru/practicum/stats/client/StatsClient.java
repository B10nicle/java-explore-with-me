package ru.practicum.stats.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.dto.HitDto;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

/**
 * @author Oleg Khilko
 */

@Service
public class StatsClient {
    private final WebClient webClient;

    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public List<ViewStatsDto> getStats(String start,
                                       String end,
                                       List<String> uris,
                                       Boolean unique) {
        var paramsUri = uris.stream().reduce("", (result, uri) -> result + "&uris=" + uri);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam(paramsUri)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .block();
    }

    public void addStats(HitDto hitDto) {
        webClient.post()
                .uri("/hit")
                .body(Mono.just(hitDto), HitDto.class)
                .exchangeToMono(response -> response.statusCode().equals(CREATED)
                        ? response.bodyToMono(Object.class).map(body -> status(CREATED).body(body))
                        : response.createException().flatMap(Mono::error))
                .block();
    }
}
