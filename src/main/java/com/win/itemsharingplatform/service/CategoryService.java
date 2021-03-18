package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.Category;
import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.repository.CategoryRepository;
import com.win.itemsharingplatform.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
}
