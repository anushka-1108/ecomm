package com.ecommerce.ecomm.controller;

import com.ecommerce.ecomm.model.Category;
import com.ecommerce.ecomm.payload.CategoryDTO;
import com.ecommerce.ecomm.payload.CategoryResponse;
import com.ecommerce.ecomm.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")

public class CategoryController {
    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message") String message){
        return new ResponseEntity<>("Echoed Message: " + message, HttpStatus.OK);
    }
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse=categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedcategoryDTO=categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedcategoryDTO, HttpStatus.CREATED);

    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO status=categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO=categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

}
