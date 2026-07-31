package com.expensetracker.expense_tracker_backend.repository;

import com.expensetracker.expense_tracker_backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}


