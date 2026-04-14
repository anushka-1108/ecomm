package com.ecommerce.ecomm.service;

import com.ecommerce.ecomm.exceptions.APIException;
import com.ecommerce.ecomm.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomm.model.Category;
import com.ecommerce.ecomm.payload.CategoryDTO;
import com.ecommerce.ecomm.payload.CategoryResponse;
import com.ecommerce.ecomm.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class  CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    //private List<Category> categories=new ArrayList<>();

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {

        Pageable pageDetails= PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No category created till Now!!!!!");
        }
        List<CategoryDTO> categoryDTOS=categories.stream()
                .map(category->modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
        }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDB != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " Already exists!!!");
        }
            Category savedCategory=categoryRepository.save(category);
            CategoryDTO savedCategoryDTO=modelMapper.map(savedCategory,CategoryDTO.class);
            return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
        Category category=modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        CategoryDTO savedCategoryDTO=modelMapper.map(savedCategory,CategoryDTO.class);
        return savedCategoryDTO;
    }

}
