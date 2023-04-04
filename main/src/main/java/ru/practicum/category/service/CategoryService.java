package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.SavedCategoryDto;

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
