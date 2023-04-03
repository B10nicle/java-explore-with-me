package ru.practicum.server.hit.entity;

import lombok.*;

/**
 * @author Oleg Khilko
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    private String uri;
    private String app;
    private long hits;
}
