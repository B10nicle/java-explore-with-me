package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.entity.User;

/**
 * @author Oleg Khilko
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByName(String name);
}
