package ru.andreyszdlv.eventscheduler.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andreyszdlv.eventscheduler.dto.category.CategoryDto;
import ru.andreyszdlv.eventscheduler.service.CategoryService;
import ru.andreyszdlv.eventscheduler.validation.RequestValidator;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final RequestValidator requestValidator;

    @PostMapping
    public CategoryDto addCategory(
            @RequestBody @Valid CategoryDto addCategoryDto,
            BindingResult bindingResult) throws BindException {

        requestValidator.validate(bindingResult);

        return categoryService.addCategory(addCategoryDto);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PatchMapping("/{id}")
    public CategoryDto updateCategory(
            @PathVariable int id,
            @RequestBody @Valid CategoryDto updateCategoryDto,
            BindingResult bindingResult) throws BindException {

        requestValidator.validate(bindingResult);

        return categoryService.updateCategory(id, updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
