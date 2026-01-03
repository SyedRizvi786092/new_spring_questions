
package com.example.expense.service;

import com.example.expense.entity.ExpenseEntity;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    ExpenseEntity create(ExpenseEntity expense);
    ExpenseEntity getById(Long id);
    List<ExpenseEntity> getAll();
    ExpenseEntity update(Long id, ExpenseEntity expense);
    boolean exists(Long id);
    void delete(Long id);
}
