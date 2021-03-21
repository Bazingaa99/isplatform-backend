package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.Category;
import com.win.itemsharingplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isp")
public class CategoryResource {
    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping("/all/categories")
    public ResponseEntity<List<Category>> getAllCategories () {
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
