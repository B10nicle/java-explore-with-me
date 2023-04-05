package ru.practicum.evm.request.service;

import ru.practicum.evm.request.dto.RequestDto;
import ru.practicum.evm.request.dto.RequestUpdateDto;
import ru.practicum.evm.request.dto.RequestUpdateResult;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface RequestService {
    List<RequestDto> getCurrentUserRequests(Long userId);

    List<RequestDto> getRequestsByOwnerOfEvent(Long userId,
                                               Long eventId);

    RequestDto cancelRequest(Long userId,
                             Long requestId);

    RequestDto saveRequest(Long userId,
                           Long eventId);

    RequestUpdateResult updateRequests(Long userId,
                                       Long eventId,
                                       RequestUpdateDto requestUpdateDto);
}
