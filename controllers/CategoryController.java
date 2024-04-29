package com.blogappapi.controllers;

import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.CategoryDTO;
import com.blogappapi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
       CategoryDTO createCat= this.categoryService.createCategory(categoryDTO);
       return new ResponseEntity(createCat, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryID}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable int categoryID){

       CategoryDTO updatedCat= this.categoryService.updateCategory(categoryDTO,categoryID);
       return new ResponseEntity<>(updatedCat,HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted",true), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
       List<CategoryDTO> allCategories= this.categoryService.getAllCategories();
       return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int categoryId){
       CategoryDTO getCategoryById= this.categoryService.getCategoryById(categoryId);
       return new ResponseEntity<>(getCategoryById,HttpStatus.OK);
    }
}
