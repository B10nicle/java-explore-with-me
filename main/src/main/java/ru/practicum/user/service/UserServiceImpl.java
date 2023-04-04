package ru.practicum.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.exception.NameExistException;
import ru.practicum.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.dto.UserDto;
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
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName()))
            throw new NameExistException("User with name " + userDto.getName() + " cannot be saved");
        var user = userMapper.toUser(userDto);
        var saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids,
                                  Integer from,
                                  Integer size) {
        return ids.isEmpty()
                ? userMapper.toUserDtos(userRepository.findAll(of(from / size, size)).toList())
                : userMapper.toUserDtos(userRepository.findAllById(ids));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
