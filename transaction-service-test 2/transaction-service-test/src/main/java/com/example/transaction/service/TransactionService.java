package com.example.transaction.service;

import com.example.transaction.dto.BalanceResponseDTO;
import com.example.transaction.dto.TransactionRequestDTO;
import com.example.transaction.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
	Transaction create(TransactionRequestDTO dto);

	Transaction update(Long id, TransactionRequestDTO dto);

	Transaction findById(Long id);

	List<Transaction> findAll();

	void delete(Long id);

	List<Transaction> filter(String type, LocalDate start, LocalDate end, Double minAmount, Double maxAmount,
			String titleContains, String descContains);

	List<Transaction> findByDate(LocalDate date);

	BalanceResponseDTO getBalance();
}
