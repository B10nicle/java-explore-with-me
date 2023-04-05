package ru.practicum.evm.category.service;

import ru.practicum.evm.category.dto.SavedCategoryDto;
import ru.practicum.evm.category.dto.CategoryDto;

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
