package com.expensetracker.expense_tracker_backend.repository;

import com.expensetracker.expense_tracker_backend.Income;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Optional<Income> findByUserIdAndFinancialYear(Long userId, String financialYear);
}
