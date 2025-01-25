package ru.andreyszdlv.eventscheduler.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.andreyszdlv.eventscheduler.dto.category.CategoryDto;
import ru.andreyszdlv.eventscheduler.model.Category;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(List<Category> categories);
}
