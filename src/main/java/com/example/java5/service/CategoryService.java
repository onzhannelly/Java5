package com.example.java5.service;

import com.example.java5.entity.Category;
import com.example.java5.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Возвращаем все категории из базы данных
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    }
}

