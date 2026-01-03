
package com.example.expense.controller;

import com.example.expense.entity.ExpenseEntity;
import com.example.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")

public class ExpenseController {

	private final ExpenseService service;

	public ExpenseController(ExpenseService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ExpenseEntity> create(@Valid @RequestBody ExpenseEntity expense) {
		// TODO: implement
		return new ResponseEntity<>(service.create(expense), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ExpenseEntity>> getAll() {
		// TODO: implement
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseEntity> getById(@PathVariable Long id) {

		if (id == null || id <= 0) {
			// return ResponseEntity.badRequest().build();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);// 400
		} else {
			ExpenseEntity expense = service.getById(id); // assume returns null if not found
			if (expense != null) {
				// return ResponseEntity.ok(expense);
				return new ResponseEntity<>(expense, HttpStatus.OK);// 200
			} else {
				// return ResponseEntity.notFound().build();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
			}
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExpenseEntity> update(@PathVariable Long id, @Valid @RequestBody ExpenseEntity expense) {
		// TODO: implement status 400/404/200

		if (id == null || id <= 0) {
			return ResponseEntity.badRequest().build(); // 400
		}

		if (service.exists(id)) {
			ExpenseEntity updated = service.update(id, expense);
			return ResponseEntity.ok(updated); // 200
		}
		// 404

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		if (id == null || id <= 0) {
			return ResponseEntity.badRequest().build(); // 400
		}

		if (service.exists(id)) {
			
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			// 404
		}
		return ResponseEntity.notFound().build();

	}
}
