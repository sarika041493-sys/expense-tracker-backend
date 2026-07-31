package com.expensetracker.expense_tracker_backend.repository;

import com.expensetracker.expense_tracker_backend.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    List<Category> findByUserId(Long userId);
}
