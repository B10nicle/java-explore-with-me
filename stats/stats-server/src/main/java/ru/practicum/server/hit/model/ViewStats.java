package ru.practicum.server.hit.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

/**
 * @author Oleg Khilko
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {
    private String uri;
    private String app;
    private Long hits;
}
