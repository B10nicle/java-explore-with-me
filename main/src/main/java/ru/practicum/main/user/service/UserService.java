package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;

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
