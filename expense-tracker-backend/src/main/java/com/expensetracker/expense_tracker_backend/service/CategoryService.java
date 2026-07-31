package com.expensetracker.expense_tracker_backend.service;

import com.expensetracker.expense_tracker_backend.Category;
import com.expensetracker.expense_tracker_backend.User;
import com.expensetracker.expense_tracker_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category, User user){
        category.setUser(user);
        return categoryRepository.save(category);
    }
    public List<Category> getAllCategoriesForUser(User user){
        return categoryRepository.findByUserId(user.getId());
    }
    public Category updateCategory(Long id, Category updatedCategory, User user){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("not authorized to update this category");
        }
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id, User user){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));
        if(!category.getUser().getId().equals(user.getId())){
        throw new RuntimeException("Not authorized to delete this category");
        }
        categoryRepository.delete(category);
    }
}
