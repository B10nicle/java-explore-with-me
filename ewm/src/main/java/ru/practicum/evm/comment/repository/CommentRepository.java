package ru.practicum.evm.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import ru.practicum.evm.comment.entity.Comment;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(Long eventId, Pageable pageable);
}
