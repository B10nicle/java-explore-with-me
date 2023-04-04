package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface UserService {
    UserDto saveUser(UserDto userDto);

    List<UserDto> getUsers(List<Long> ids,
                           Integer from,
                           Integer size);

    void deleteUser(Long id);
}
