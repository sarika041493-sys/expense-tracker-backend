package com.expensetracker.expense_tracker_backend;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public BigDecimal getAmount(){
        return amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getFinancialYear(){
        return financialYear;
    }
    public void setFinancialYear(String financialYear){
        this.financialYear = financialYear;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
}
