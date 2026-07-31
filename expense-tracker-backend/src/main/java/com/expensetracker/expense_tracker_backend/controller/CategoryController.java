package com.expensetracker.expense_tracker_backend.controller;

import com.expensetracker.expense_tracker_backend.Category;
import com.expensetracker.expense_tracker_backend.User;
import com.expensetracker.expense_tracker_backend.repository.CategoryRepository;
import com.expensetracker.expense_tracker_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
       User defaultUser = userRepository.findById(1L)
               .orElseThrow(()-> new RuntimeException("Default user not found"));
       category.setUser(defaultUser);
       Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category updated){
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    categoryRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
