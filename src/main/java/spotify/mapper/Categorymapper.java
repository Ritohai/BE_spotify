package spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spotify.model.dto.CategoryDTO;
import spotify.model.entity.Category;

@Mapper(componentModel = "spring")
public interface Categorymapper {
    Categorymapper INSTANCE = Mappers.getMapper(Categorymapper.class);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
    CategoryDTO categoryToCategoryDTO(Category category);
}
