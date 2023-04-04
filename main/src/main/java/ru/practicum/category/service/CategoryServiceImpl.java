package ru.practicum.category.service;

import ru.practicum.category.exception.CategoryNotEmptyException;
import ru.practicum.category.exception.CategoryNotExistException;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.user.exception.NameExistException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.dto.SavedCategoryDto;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
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
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto saveCategory(SavedCategoryDto savedCategoryDto) {
        if (categoryRepository.existsByName(savedCategoryDto.getName()))
            throw new NameExistException("Category with name " + savedCategoryDto.getName() + " cannot be saved");
        var entity = categoryMapper.toCategory(savedCategoryDto);
        var saved = categoryRepository.save(entity);
        return categoryMapper.toCategoryDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotExistException("Category#" + id + " does not exist"));
        if (categoryRepository.existsByName(categoryDto.getName()))
            throw new NameExistException("Category with name " + categoryDto.getName() + " cannot be updated");
        category.setName(categoryDto.getName());
        var saved = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(saved);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (eventRepository.existsByCategoryId(id))
            throw new CategoryNotEmptyException("Category#" + id + " is not empty");
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return categoryMapper.toCategoryDtos(categoryRepository.findAll(of(from / size, size)).toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotExistException("Category#" + id + " does not exist"));
        return categoryMapper.toCategoryDto(category);
    }
}
