package com.blogappapi.services.impl;

import com.blogappapi.entities.Category;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.CategoryDTO;
import com.blogappapi.repos.CategoryRepo;
import com.blogappapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=this.modelMapper.map(categoryDTO,Category.class);
        Category createdCategory= this.categoryRepo.save(category);
        return this.modelMapper.map(createdCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        Category updatedCategory= this.categoryRepo.save(category);

        return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(int id) {
    Category category= this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
    this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(int categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));

        return this.modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories=this.categoryRepo.findAll();
       List<CategoryDTO> getAll= categories.stream().map((category -> this.modelMapper.map(category,CategoryDTO.class))).collect(Collectors.toList());
        return getAll;
    }
}
