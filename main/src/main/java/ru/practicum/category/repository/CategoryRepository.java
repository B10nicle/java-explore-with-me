package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.entity.Category;

/**
 * @author Oleg Khilko
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);
}
