package ru.practicum.evm.comment.mapper;

import ru.practicum.evm.comment.dto.CommentDto;
import ru.practicum.evm.comment.entity.Comment;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "event.id", target = "eventId")
    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDtos(List<Comment> comments);
}
