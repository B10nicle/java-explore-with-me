package ru.practicum.main.user.controller;

import org.springframework.validation.annotation.Validated;
import ru.practicum.main.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.UserDto;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin")
public class UserAdminController {
    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(CREATED)
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "0") int from) {
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
