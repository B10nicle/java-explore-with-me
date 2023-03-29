package ru.practicum.stats.server.hit.mapper;

import ru.practicum.stats.server.hit.model.EndpointHit;
import ru.practicum.stats.dto.EndpointHitDto;

public class EndpointHitMapper {
    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .id(endpointHitDto.getId())
                .uri(endpointHitDto.getUri())
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .id(endpointHit.getId())
                .uri(endpointHit.getUri())
                .app(endpointHit.getApp())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}
