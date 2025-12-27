package com.example.transaction.mapper;

import com.example.transaction.dto.TransactionRequestDTO;
import com.example.transaction.dto.TransactionResponseDTO;
import com.example.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

	public Transaction toEntity(TransactionRequestDTO dto) {
		Transaction t = new Transaction();
		
		t.setAmount(dto.getAmount());
		t.setDate(dto.getDate());
		t.setDescription(dto.getDescription());
		t.setTitle(dto.getTitle());
		t.setType(dto.getType());
		return t;
	}

	public TransactionResponseDTO toDTO(Transaction t) {
		TransactionResponseDTO dto = new TransactionResponseDTO();
		
		dto.setAmount(t.getAmount());
		dto.setDate(t.getDate());
		dto.setDescription(t.getDescription());
		dto.setTitle(t.getTitle());
		dto.setType(t.getType());
		dto.setId(t.getId());
		
		return dto;
	}
}
