package com.expensetracker.expense_tracker_backend.repository;

import com.expensetracker.expense_tracker_backend.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findByuserId(Long userId);
    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);
}