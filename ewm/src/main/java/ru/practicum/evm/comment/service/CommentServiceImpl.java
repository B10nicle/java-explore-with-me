package ru.practicum.evm.comment.service;

import ru.practicum.evm.comment.exception.UsernameInCommentException;
import ru.practicum.evm.comment.exception.CommentNotExistException;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.evm.event.exception.EventWrongTimeException;
import ru.practicum.evm.event.exception.EventNotExistException;
import ru.practicum.evm.user.exception.UserNotExistException;
import ru.practicum.evm.comment.repository.CommentRepository;
import ru.practicum.evm.event.repository.EventRepository;
import ru.practicum.evm.user.repository.UserRepository;
import ru.practicum.evm.comment.mapper.CommentMapper;
import ru.practicum.evm.comment.dto.SavedCommentDto;
import ru.practicum.evm.comment.dto.CommentDto;
import ru.practicum.evm.comment.entity.Comment;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static java.time.LocalDateTime.*;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentsRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public CommentDto saveComment(SavedCommentDto savedCommentDto,
                                  Long userId,
                                  Long eventId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException("User#" + userId + "does not exist"));
        var event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + "does not exist"));

        var comment = Comment.builder()
                .user(user)
                .event(event)
                .created(now())
                .text(savedCommentDto.getText())
                .build();

        var saved = commentsRepository.save(comment);
        return commentMapper.toCommentDto(saved);
    }

    @Override
    @Transactional
    public CommentDto updateCommentByAdmin(SavedCommentDto savedCommentDto,
                                           Long commentId) {
        var old = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotExistException("Comment does not exist"));

        old.setText(savedCommentDto.getText());
        var saved = commentsRepository.save(old);

        return commentMapper.toCommentDto(saved);
    }

    @Override
    @Transactional
    public CommentDto updateCommentByUser(SavedCommentDto savedCommentDto,
                                          Long userId,
                                          Long commentId) {
        var old = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotExistException("Comment does not exist"));

        if (!old.getUser().getId().equals(userId))
            throw new UsernameInCommentException("Username in comment is different");
        if (!userRepository.existsById(userId))
            throw new UserNotExistException("User does not exist");

        old.setText(savedCommentDto.getText());
        var saved = commentsRepository.save(old);

        return commentMapper.toCommentDto(saved);
    }

    @Override
    public CommentDto getCommentByAdmin(Long commentId) {
        var comment = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotExistException("Comment does not exist"));
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto getCommentByUser(Long userId,
                                       Long commentId) {
        var comment = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotExistException("Comment does not exist"));

        if (!userRepository.existsById(userId))
            throw new UserNotExistException("User does not exist");
        if (!userId.equals(comment.getUser().getId()))
            throw new UsernameInCommentException("Username in comment is different");

        return commentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByAdmin(Long eventId,
                                               Integer from,
                                               Integer size) {
        eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistException("Event#" + eventId + "does not exist"));

        var comments = commentsRepository.findAllByEventId(eventId, of(from / size, size));
        return commentMapper.toCommentDtos(comments);
    }

    @Override
    public List<CommentDto> getCommentsByUserAndTime(Long userId,
                                                     LocalDateTime createdStart,
                                                     LocalDateTime createdEnd,
                                                     Integer from,
                                                     Integer size) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Comment.class);
        var root = query.from(Comment.class);
        var criteria = root.get("user").in(userId);

        if (createdStart != null && createdEnd != null && createdEnd.isBefore(createdStart))
            throw new EventWrongTimeException("Wrong time. End must be after start");

        if (createdStart != null) {
            var greater = builder.greaterThanOrEqualTo(
                    root.get("created").as(LocalDateTime.class), createdStart);
            criteria = builder.and(criteria, greater);
        }
        if (createdEnd != null) {
            var less = builder.lessThanOrEqualTo(
                    root.get("created").as(LocalDateTime.class), createdEnd);
            criteria = builder.and(criteria, less);
        }

        query.select(root)
                .where(criteria)
                .orderBy(builder.asc(root.get("created")));

        var comments = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        return commentMapper.toCommentDtos(comments);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        commentsRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(Long userId,
                                    Long commentId) {
        var comment = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotExistException("Comment does not exist"));

        if (!userRepository.existsById(userId))
            throw new UserNotExistException("User does not exist");
        if (!comment.getUser().getId().equals(userId))
            throw new UsernameInCommentException("Username in comment is different");

        commentsRepository.delete(comment);
    }
}
