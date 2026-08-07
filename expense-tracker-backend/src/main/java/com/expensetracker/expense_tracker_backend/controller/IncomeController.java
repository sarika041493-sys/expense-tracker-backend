package com.expensetracker.expense_tracker_backend.controller;

import com.expensetracker.expense_tracker_backend.Income;
import com.expensetracker.expense_tracker_backend.User;
import com.expensetracker.expense_tracker_backend.repository.ExpenseRepository;
import com.expensetracker.expense_tracker_backend.repository.IncomeRepository;
import com.expensetracker.expense_tracker_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Income addOrUpdateIncome(@RequestBody Income incomeRequest){
        User defaultUser = userRepository.findAll().get(0);

        Optional<Income> existing = incomeRepository.findByUserIdAndFinancialYear(
                defaultUser.getId(), incomeRequest.getFinancialYear());

        Income income = existing.orElse(new Income());
        income.setAmount(incomeRequest.getAmount());
        income.setFinancialYear(incomeRequest.getFinancialYear());
        income.setUser(defaultUser);

        return incomeRepository.save(income);
    }

    @GetMapping("/{financialYear}")
    public Income getIncome(@PathVariable String financialYear){
        User defaultUser = userRepository.findAll().get(0);
        return incomeRepository.findByUserIdAndFinancialYear(defaultUser.getId(),financialYear)
                .orElse(null);
    }

    @GetMapping("/summary/{financialYear}")
    public Map<String, Object>getSummary(@PathVariable String financialYear){
        User defaultUser = userRepository.findAll().get(0);

        BigDecimal salary = incomeRepository
                .findByUserIdAndFinancialYear(defaultUser.getId(),financialYear)
                .map(Income::getAmount)
                .orElse(BigDecimal.ZERO);

        int startYear = Integer.parseInt(financialYear.split("-")[0]);
        LocalDate fyStart = LocalDate.of(startYear,4,1);
        LocalDate fyEnd = LocalDate.of(startYear +1,3,31);

        BigDecimal totalExpenses = expenseRepository
                .findByUserIdAndDateBetween(defaultUser.getId(), fyStart, fyEnd)
                .stream()
                .map(e-> e.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal savings = salary.subtract(totalExpenses);

        Map<String, Object> summary = new HashMap<>();
        summary.put("financialYear", financialYear);
        summary.put("salary", salary);
        summary.put("totalExpenses", totalExpenses);
        summary.put("savings", savings);

        return summary;

    }
}
