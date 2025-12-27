package com.example.transaction.repository;

import com.example.transaction.entity.Transaction;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TransactionRepository {
	private final Map<Long, Transaction> storage = new HashMap<>();
	private long sequence = 1;

	public Transaction save(Transaction transaction) {
		if (transaction.getId() == null)
			transaction.setId(sequence++);
		storage.put(transaction.getId(), transaction);
		return transaction;
	}

	public Optional<Transaction> findById(Long id) {
		return Optional.ofNullable(storage.get(id));
	}

	public List<Transaction> findAll() {
		return new ArrayList<>(storage.values());
	}

	public void deleteById(Long id) {
		storage.remove(id);
	}

	public boolean existsById(Long id) {
		return storage.containsKey(id);
	}
}
