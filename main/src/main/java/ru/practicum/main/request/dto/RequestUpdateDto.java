package ru.practicum.main.request.dto;

import ru.practicum.main.request.enums.RequestUpdateStatus;
import lombok.*;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {
    private RequestUpdateStatus status;
    private List<Long> requestIds;
}
