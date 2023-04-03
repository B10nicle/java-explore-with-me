package ru.practicum.main.category.controller;

import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.category.dto.CategoryDto;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author Oleg Khilko
 */

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "10", required = false) int size,
                                           @RequestParam(defaultValue = "0", required = false) int from) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        return categoryService.getCategory(catId);
    }
}
