package spotify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotify.exception.customer.CustomerException;
import spotify.model.dto.CategoryDTO;
import spotify.model.entity.Category;
import spotify.service.category.ICategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

//  TODO: Thêm danh mục
    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws CustomerException {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

//  TODO: Sửa danh mục
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDTO),HttpStatus.OK);
    }

//    TODO: Ẩn danh mục theo Id
        @PatchMapping("/status/{id}")
    public ResponseEntity<String> changStatus(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(categoryService.changStatus(id),HttpStatus.OK);
    }

//     TODO: Tìm kiếm theo id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> fingById(@PathVariable Long id) throws CustomerException {
        return new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }

}
