
package com.example.expense.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.expense.entity.ExpenseEntity;
import com.example.expense.repository.ExpenseRepository;
import com.example.expense.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository repo;

	public ExpenseServiceImpl(ExpenseRepository repo) {
		this.repo = repo;
	}

	@Override
	public ExpenseEntity create(ExpenseEntity expense) {
		expense.setId(null);
		return repo.save(expense);
	}

	@Override
	public ExpenseEntity getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<ExpenseEntity> getAll() {
		return repo.findAll();
	}

	@Override
	public ExpenseEntity update(Long id, ExpenseEntity expense) {

		expense.setId(id);
		return repo.save(expense);
	}

	@Override
	public boolean exists(Long id) {
		return repo.existsById(id);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
