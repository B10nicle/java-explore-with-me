package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.SavedCategoryDto;
import ru.practicum.main.category.dto.CategoryDto;

import java.util.List;

/**
 * @author Oleg Khilko
 */

public interface CategoryService {
    CategoryDto saveCategory(SavedCategoryDto savedCategoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(Long id);
}
