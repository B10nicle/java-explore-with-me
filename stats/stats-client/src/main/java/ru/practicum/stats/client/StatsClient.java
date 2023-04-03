package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.exception.JsonException;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.dto.HitDto;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static java.util.Arrays.*;

/**
 * @author Oleg Khilko
 */

@Service
public class StatsClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public StatsClient(@Value("${stats-server.url}") String serverUrl,
                       RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    public List<ViewStatsDto> getStats(String start,
                                       String end,
                                       List<String> uris,
                                       Boolean unique) {
        var parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("uris", uris);
        parameters.put("unique", unique);
        var response = restTemplate.getForEntity(
                serverUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                String.class,
                parameters);
        try {
            return asList(new ObjectMapper().readValue(response.getBody(), ViewStatsDto[].class));
        } catch (JsonProcessingException exception) {
            throw new JsonException("JSON error: " + exception.getMessage());
        }
    }

    public void addStats(HitDto hitDto) {
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        var request = new HttpEntity<>(hitDto, headers);
        restTemplate.exchange(
                serverUrl + "/hit",
                POST,
                request,
                HitDto.class);
    }
}
