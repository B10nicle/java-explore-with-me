package ru.practicum.stats.client;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    @PostMapping()
    public ResponseEntity<Object> createHit(@RequestBody HitDto hitDto) {
        return post("/hit", hitDto);
    }

    @GetMapping()
    public ResponseEntity<Object> getStats(@RequestParam(value = "start") LocalDateTime start,
                                           @RequestParam(value = "end") LocalDateTime end,
                                           @RequestParam(required = false, defaultValue = "false") boolean unique,
                                           @RequestParam(required = false) List<String> uris) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats/?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}
