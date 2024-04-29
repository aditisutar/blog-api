package com.blogappapi.services;

import com.blogappapi.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

     CategoryDTO createCategory(CategoryDTO categoryDTO );
     CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryId);
     void deleteCategory(int id);
     CategoryDTO getCategoryById(int categoryId);
     List<CategoryDTO> getAllCategories();

}
