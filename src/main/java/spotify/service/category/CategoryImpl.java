package spotify.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spotify.exception.customer.CustomerException;
import spotify.mapper.Categorymapper;
import spotify.model.dto.CategoryDTO;
import spotify.model.entity.Category;
import spotify.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String createCategory(CategoryDTO categoryDTO) throws CustomerException {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new CustomerException("Tên danh mục đã tồn tại");
        }
        Category category = Categorymapper.INSTANCE.categoryDTOToCategory(categoryDTO);
        category.setStatus(true);
        categoryRepository.save(category);
        return "Đăng kí thành công.";
    }

    @Override
    public String updateCategory(Long id, CategoryDTO categoryDTO) throws CustomerException {
        List<Category> categoryList = categoryRepository.findByCategoryNameAndCategoryIdNot(categoryDTO.getCategoryName(), id);
        if (findById(id) != null && categoryRepository.findById(id).get().getStatus()) {
            if (!categoryList.isEmpty()) {
                throw new CustomerException("Tên danh mục đã tồn tại.");
            }
            Category category = Categorymapper.INSTANCE.categoryDTOToCategory(categoryDTO);
            category.setCategoryId(id);
            category.setStatus(categoryRepository.findById(id).get().getStatus());
            categoryRepository.save(category);
            return "Sửa thành công.";
        } else {
            throw new CustomerException("Không tìm thấy id");
        }
    }

    @Override
    public CategoryDTO findById(Long id) throws CustomerException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return Categorymapper.INSTANCE.categoryToCategoryDTO(category.get());
        }
        throw new CustomerException("Không tìm thấy id.");
    }

    @Override
    public String changStatus(Long id) throws CustomerException {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setStatus(!optional.get().getStatus());
            categoryRepository.save(category);
            return "Thay đổi trạng thái thành công.";
        }
        throw new CustomerException("Không tìm thấy id.");
    }


}
