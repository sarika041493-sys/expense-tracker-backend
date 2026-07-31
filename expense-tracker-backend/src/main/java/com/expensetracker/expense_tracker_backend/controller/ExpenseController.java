package com.expensetracker.expense_tracker_backend.controller;

import com.expensetracker.expense_tracker_backend.User;
import com.expensetracker.expense_tracker_backend.repository.ExpenseRepository;
import com.expensetracker.expense_tracker_backend.Expense;
import com.expensetracker.expense_tracker_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(){
        return ResponseEntity.ok(expenseRepository.findAll());
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        User defaultUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Default user not found"));
        expense.setUser(defaultUser);
        return expenseRepository.save(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateExpense(@PathVariable Long id, @RequestBody Expense updated){
        return expenseRepository.findById(id)
                .map(existing->{
                    existing.setAmount(updated.getAmount());
                    existing.setDate(updated.getDate());
                    existing.setNotes(updated.getNotes());
                    existing.setCategory(updated.getCategory());
                    expenseRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id){
        if(!expenseRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }
}
