package ru.practicum.main.category.controller;

import org.springframework.validation.annotation.Validated;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.category.dto.SavedCategoryDto;
import ru.practicum.main.category.dto.CategoryDto;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Oleg Khilko
 */

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @ResponseStatus(CREATED)
    @PostMapping("/categories")
    public CategoryDto saveCategory(@Valid @RequestBody SavedCategoryDto savedCategoryDto) {
        return categoryService.saveCategory(savedCategoryDto);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                      @PathVariable Long catId) {
        return categoryService.updateCategory(catId, categoryDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }
}
