package ru.practicum.evm.comment.controller;

import ru.practicum.evm.comment.service.CommentService;
import ru.practicum.evm.comment.dto.SavedCommentDto;
import org.springframework.web.bind.annotation.*;
import ru.practicum.evm.comment.dto.CommentDto;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class CommentAdminController {
    private final CommentService commentService;

    @PatchMapping("/comments/{commentId}")
    public CommentDto updateComment(@Valid @RequestBody SavedCommentDto newCommentDto,
                                    @PathVariable Long commentId) {
        return commentService.updateCommentByAdmin(newCommentDto, commentId);
    }

    @GetMapping("/comments")
    public List<CommentDto> getCommentsByEventId(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @RequestParam Long eventId) {
        return commentService.getCommentsByAdmin(eventId, from, size);
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentByAdmin(commentId);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }
}
