package com.example.transaction.controller;

import com.example.transaction.dto.*;
import com.example.transaction.entity.Transaction;
import com.example.transaction.mapper.TransactionMapper;
import com.example.transaction.service.TransactionService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService service;
	@Autowired
	private TransactionMapper mapper;

	@PostMapping
	public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO dto) {
		Transaction t = service.create(dto);
		TransactionResponseDTO ddto = mapper.toDTO(t);
		return new ResponseEntity<>(ddto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionResponseDTO> getById(@PathVariable Long id) {

		Transaction t = service.findById(id);
		TransactionResponseDTO d = mapper.toDTO(t);

		return new ResponseEntity<TransactionResponseDTO>(d, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TransactionResponseDTO>> getAll() {

		List<Transaction> li = service.findAll();
		List<TransactionResponseDTO> dto = new ArrayList<>();
		for (Transaction t : li) {
			TransactionResponseDTO d = mapper.toDTO(t);
			dto.add(d);
		}
		return new ResponseEntity<List<TransactionResponseDTO>>(dto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id,
			@Valid @RequestBody TransactionRequestDTO dto) {
		Transaction t = service.update(id, dto);
		TransactionResponseDTO dt = mapper.toDTO(t);

		return new ResponseEntity<TransactionResponseDTO>(dt, HttpStatus.OK);
	}

	@GetMapping("/filter")
	public ResponseEntity<List<TransactionResponseDTO>> filter(String type, String startDate, String endDate,
			Double minAmount, Double maxAmount, String titleContains, String descriptionContains) {
		LocalDate startd = LocalDate.parse(startDate);
		LocalDate endd = LocalDate.parse(endDate);
		List<Transaction> li = service.filter(type, startd, endd, minAmount, maxAmount, titleContains,
				descriptionContains);
		
		if(li.isEmpty()) {
			return ResponseEntity.status(400).body(null);
		}
		List<TransactionResponseDTO> dto = new ArrayList<>();
		for (Transaction t : li) {
			TransactionResponseDTO d = mapper.toDTO(t);
			dto.add(d);
		}
		return new ResponseEntity<List<TransactionResponseDTO>>(dto, HttpStatus.OK);

	}

	@GetMapping("/date/{date}")
	public ResponseEntity<List<TransactionResponseDTO>> date(String date) {
		LocalDate dt = LocalDate.parse(date);
		List<Transaction> li = service.findByDate(dt);
		List<TransactionResponseDTO> dto = new ArrayList<>();
		for (Transaction t : li) {
			TransactionResponseDTO d = mapper.toDTO(t);
			dto.add(d);
		}
		return new ResponseEntity<List<TransactionResponseDTO>>(dto, HttpStatus.OK);
	}

	@GetMapping("/balance")
	public ResponseEntity<BalanceResponseDTO> balance() {
		return new ResponseEntity<BalanceResponseDTO>(service.getBalance(), HttpStatus.OK);
	}
}
