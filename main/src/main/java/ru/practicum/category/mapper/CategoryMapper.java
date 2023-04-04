package ru.practicum.category.mapper;

import ru.practicum.category.dto.SavedCategoryDto;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(SavedCategoryDto newCategoryDto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtos(List<Category> categoryList);
}
