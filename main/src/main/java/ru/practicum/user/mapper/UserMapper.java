package ru.practicum.user.mapper;

import ru.practicum.user.dto.UserDto;
import ru.practicum.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userModelDto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> usersList);
}

