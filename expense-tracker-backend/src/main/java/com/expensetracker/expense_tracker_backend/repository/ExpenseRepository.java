package com.expensetracker.expense_tracker_backend.repository;

import com.expensetracker.expense_tracker_backend.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);
    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}