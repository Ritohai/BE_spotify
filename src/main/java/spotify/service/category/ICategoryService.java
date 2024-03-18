package spotify.service.category;

import spotify.exception.customer.CustomerException;
import spotify.model.dto.CategoryDTO;

public interface ICategoryService {
    String createCategory(CategoryDTO categoryDTO) throws CustomerException;

    String updateCategory(Long id, CategoryDTO categoryDTO) throws CustomerException;

    String changStatus(Long id) throws CustomerException;
    CategoryDTO findById(Long id) throws CustomerException;
}
