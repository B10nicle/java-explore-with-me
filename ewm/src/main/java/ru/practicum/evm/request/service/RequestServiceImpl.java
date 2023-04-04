package ru.practicum.evm.request.service;

import ru.practicum.evm.event.exception.EventNotExistException;
import ru.practicum.evm.event.exception.EventNotPublishedException;
import ru.practicum.evm.event.repository.EventRepository;
import ru.practicum.evm.request.dto.RequestDto;
import ru.practicum.evm.request.dto.RequestUpdateDto;
import ru.practicum.evm.request.dto.RequestUpdateResult;
import ru.practicum.evm.request.entity.Request;
import ru.practicum.evm.request.enums.RequestStatus;
import ru.practicum.evm.request.enums.RequestUpdateStatus;
import ru.practicum.evm.request.exception.RequestConfirmedException;
import ru.practicum.evm.request.exception.RequestExistException;
import ru.practicum.evm.request.exception.RequestNotExistException;
import ru.practicum.evm.request.exception.RequestParticipantLimitException;
import ru.practicum.evm.request.mapper.RequestMapper;
import ru.practicum.evm.request.repository.RequestRepository;
import ru.practicum.evm.user.exception.UserNotExistException;
import ru.practicum.evm.user.exception.WrongUserException;
import ru.practicum.evm.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.*;
import static java.time.LocalDateTime.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<RequestDto> getCurrentUserRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException("User#" + userId + " does not exist"));

        return requestMapper.toRequestDtos(requestRepository.findAllByRequester(userId));
    }

    @Override
    public List<RequestDto> getRequestsByOwnerOfEvent(Long userId, Long eventId) {
        return requestMapper.toRequestDtos(requestRepository.findAllByEventWithInitiator(userId, eventId));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        var request = requestRepository.findByRequesterAndId(userId, requestId).orElseThrow(
                () -> new RequestNotExistException("Request#" + requestId + " does not exist"));
        request.setStatus(RequestStatus.CANCELED);

        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto saveRequest(Long userId, Long eventId) {
        if (requestRepository.existsByRequesterAndEvent(userId, eventId))
            throw new RequestExistException("Request is already exist");

        var event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + " does not exist"));

        if (event.getInitiator().getId().equals(userId))
            throw new WrongUserException("Request cannot be created by the initiator#" + userId);
        if (event.getPublishedOn() == null)
            throw new EventNotPublishedException("Event has not been published yet");

        var requests = requestRepository.findAllByEvent(eventId);

        if (!event.getRequestModeration() && requests.size() >= event.getParticipantLimit())
            throw new RequestParticipantLimitException("Participant limit has been exceeded");

        var request = Request.builder()
                .created(now())
                .event(eventId)
                .requester(userId)
                .status(RequestStatus.PENDING)
                .build();

        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestUpdateResult updateRequests(Long userId, Long eventId, RequestUpdateDto requestUpdateDto) {
        var event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + " does not exist"));

        var result = new RequestUpdateResult();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) return result;

        var requests = requestRepository.findAllByEventWithInitiator(userId, eventId);
        var requestsToUpdate = requests.stream()
                .filter(val -> requestUpdateDto.getRequestIds().contains(val.getId()))
                .collect(toList());

        if (requestsToUpdate.stream()
                .anyMatch(request -> RequestStatus.CONFIRMED.equals(request.getStatus())
                        && RequestUpdateStatus.REJECTED.equals(requestUpdateDto.getStatus())))
            throw new RequestConfirmedException("Request has been already confirmed");
        if (event.getConfirmedRequests() + requestsToUpdate.size() > event.getParticipantLimit()
                && RequestUpdateStatus.CONFIRMED.equals(requestUpdateDto.getStatus()))
            throw new RequestParticipantLimitException("Participants limit has been exceeded");

        for (var request : requestsToUpdate)
            request.setStatus(RequestStatus.valueOf(requestUpdateDto.getStatus().toString()));

        requestRepository.saveAll(requestsToUpdate);

        if (RequestUpdateStatus.CONFIRMED.equals(requestUpdateDto.getStatus()))
            event.setConfirmedRequests(requestsToUpdate.size() + event.getConfirmedRequests());

        eventRepository.save(event);

        if (RequestUpdateStatus.REJECTED.equals(requestUpdateDto.getStatus()))
            result.setRejectedRequests(requestMapper.toRequestDtos(requestsToUpdate));
        if (RequestUpdateStatus.CONFIRMED.equals(requestUpdateDto.getStatus()))
            result.setConfirmedRequests(requestMapper.toRequestDtos(requestsToUpdate));

        return result;
    }
}
