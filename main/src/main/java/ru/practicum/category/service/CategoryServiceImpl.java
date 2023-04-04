package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.SavedCategoryDto;
import ru.practicum.category.exception.CategoryNotEmptyException;
import ru.practicum.category.exception.CategoryNotExistException;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.exception.NameExistException;
import org.springframework.stereotype.Service;
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
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto saveCategory(SavedCategoryDto savedCategoryDto) {
        if (categoryRepository.existsByName(savedCategoryDto.getName()))
            throw new NameExistException("Category with name " + savedCategoryDto.getName() + " cannot be saved");
        log.debug("Category " + savedCategoryDto.getName() + " was saved");
        var entity = categoryMapper.toCategory(savedCategoryDto);
        var saved = categoryRepository.save(entity);
        return categoryMapper.toCategoryDto(saved);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotExistException("Category#" + id + " does not exist"));
        if (categoryRepository.existsByName(categoryDto.getName()))
            throw new NameExistException("Category with name " + categoryDto.getName() + " cannot be updated");
        log.debug("Category's name was updated");
        category.setName(categoryDto.getName());
        var saved = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        if (eventRepository.existsByCategoryId(id))
            throw new CategoryNotEmptyException("Category#" + id + " is not empty");
        log.debug("Category#" + id + " was deleted");
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.debug("List of categories was received");
        return categoryMapper.toCategoryDtos(categoryRepository.findAll(of(from / size, size)).toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotExistException("Category#" + id + " does not exist"));
        log.debug("Category#" + id + " was received");
        return categoryMapper.toCategoryDto(category);
    }
}
