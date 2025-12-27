package com.example.transaction.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.transaction.dto.BalanceResponseDTO;
import com.example.transaction.dto.TransactionRequestDTO;
import com.example.transaction.entity.Transaction;
import com.example.transaction.exception.ResourceNotFoundException;
import com.example.transaction.mapper.TransactionMapper;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private final TransactionRepository repo;
	@Autowired
	private final TransactionMapper mapper;

	public TransactionServiceImpl(TransactionRepository repo, TransactionMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public Transaction create(TransactionRequestDTO dto) {
		Transaction t = mapper.toEntity(dto);

		Transaction saved = repo.save(t);

		return saved;
	}

	public Transaction update(Long id, TransactionRequestDTO dto) {
		Transaction t = findById(id);
		t.setType(dto.getType());
		t.setTitle(dto.getTitle());
		t.setAmount(dto.getAmount());
		t.setDate(dto.getDate());
		t.setDescription(dto.getDescription());
		return repo.save(t);
	}

	public Transaction findById(Long id) {

		Transaction t = repo.findById(id).orElse(null);

		if (t == null) {
			throw new ResourceNotFoundException("id" + id + "not found");
		}
		return t;
	}

	public List<Transaction> findAll() {
		List<Transaction> t = repo.findAll();

		if (t.isEmpty()) {
			throw new ResourceNotFoundException("Not 404");
		}
		return t;
	}

	public void delete(Long id) {
		Transaction t = repo.findById(id).orElse(null);

		if (t == null) {
			throw new ResourceNotFoundException("id" + id + "not found");
		}

		repo.deleteById(id);
	}

	public List<Transaction> filter(String type, LocalDate start, LocalDate end, Double minAmount, Double maxAmount,
			String titleContains, String descContains) {
		List<Transaction> allTransactions = repo.findAll();
		List<Transaction> matchingTypes = new ArrayList<>();
		List<Transaction> transactionTypesBelongToMinAndMax = new ArrayList<>();
		for (Transaction t : allTransactions) {
			if (type != null && t.getType().equals(type)) {
				matchingTypes.add(t);
				return matchingTypes;
			}
		}

		if (null != start && null != end)
			if (start.isAfter(end)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

		if (minAmount != null && maxAmount != 0)
			if (minAmount > maxAmount) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		for (Transaction t : allTransactions) {
			if (t.getAmount().compareTo(BigDecimal.valueOf(minAmount)) >= 0
					&& t.getAmount().compareTo(BigDecimal.valueOf(maxAmount)) <= 0) {
				transactionTypesBelongToMinAndMax.add(t);
			}
		}
		
		System.out.println("Size of transactions are: " + transactionTypesBelongToMinAndMax.size());
		//System.out.println("Size of transactions are: " + transactionTypesBelongToMinAndMax.size());
		
		if (transactionTypesBelongToMinAndMax.isEmpty()) {
			return null;
		}
		return transactionTypesBelongToMinAndMax;

	}

	public List<Transaction> findByDate(LocalDate date) {
			List<Transaction> transactions = 
					repo.findAll().stream().filter((tx) -> tx.getDate().equals(date)).toList();
			
		return transactions;
	}

	public BalanceResponseDTO getBalance() {
		BigDecimal credit = BigDecimal.ZERO;
		BigDecimal debit = BigDecimal.ZERO;
		List<Transaction> li = repo.findAll();

		for (Transaction tx : li) {
			if (tx.getType().equals("CREDIT")) {
				credit= credit.add(tx.getAmount());
			}
			if (tx.getType().equals("DEBIT")) {
				debit = debit.add(tx.getAmount());
			}
		}
		return new BalanceResponseDTO(credit, debit);
	}
}
