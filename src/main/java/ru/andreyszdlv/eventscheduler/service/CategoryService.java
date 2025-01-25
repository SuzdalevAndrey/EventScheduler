package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreyszdlv.eventscheduler.dto.category.CategoryDto;
import ru.andreyszdlv.eventscheduler.exception.AccessDeniedException;
import ru.andreyszdlv.eventscheduler.exception.CategoryAlreadyExistsException;
import ru.andreyszdlv.eventscheduler.exception.CategoryNotFoundException;
import ru.andreyszdlv.eventscheduler.mapper.CategoryMapper;
import ru.andreyszdlv.eventscheduler.model.Category;
import ru.andreyszdlv.eventscheduler.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UserService userService;

    public CategoryDto addCategory(CategoryDto addCategoryDto) {
        if(categoryRepository.existsByName(addCategoryDto.name()))
            throw new CategoryAlreadyExistsException("error.409.category.already_exists");

        Category category = categoryMapper.toEntity(addCategoryDto);
        category.setAuthorId(userService.getCurrentUserId());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public CategoryDto updateCategory(int id, CategoryDto updateCategoryDto) {

        if(!categoryRepository.existsById(id))
            throw new CategoryNotFoundException("error.404.category.not_found");

        if(categoryRepository.findById(id).get().getAuthorId() != userService.getCurrentUserId())
            throw new AccessDeniedException("error.403.access.denied");

        Category category = categoryMapper.toEntity(updateCategoryDto);
        category.setId(id);

        return categoryMapper.toDto(categoryRepository.update(category));
    }

    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDto(categoryRepository.findAllByAuthorId(userService.getCurrentUserId()));
    }

    public void deleteCategory(int id) {

        if(!categoryRepository.existsById(id))
            throw new CategoryNotFoundException("error.404.category.not_found");

        if(categoryRepository.findById(id).get().getAuthorId() != userService.getCurrentUserId())
            throw new AccessDeniedException("error.403.access.denied");

        categoryRepository.deleteById(id);
    }
}
