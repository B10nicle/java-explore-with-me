package ru.practicum.user.dto;

import lombok.*;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUserDto {
    private Long id;
    private String name;
}
