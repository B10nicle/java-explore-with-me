package ru.practicum.main.user.service;

import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.exception.NameExistException;
import ru.practicum.main.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import ru.practicum.main.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.springframework.data.domain.PageRequest.*;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName()))
            throw new NameExistException("User with name " + userDto.getName() + " cannot be saved");
        log.debug("User " + userDto.getName() + " was saved");
        var user = userMapper.toUser(userDto);
        var saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        log.debug("List of users' ids was received");
        return ids.isEmpty()
                ? userMapper.toUserDtos(userRepository.findAll(of(from / size, size)).toList())
                : userMapper.toUserDtos(userRepository.findAllById(ids));
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("User#" + id + " was deleted");
        userRepository.deleteById(id);
    }
}
