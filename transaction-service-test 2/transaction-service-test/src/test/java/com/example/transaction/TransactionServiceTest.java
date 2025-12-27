package com.example.transaction;

import com.example.transaction.dto.TransactionRequestDTO;
import com.example.transaction.entity.Transaction;
import com.example.transaction.exception.ResourceNotFoundException;
import com.example.transaction.mapper.TransactionMapper;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;
import com.example.transaction.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

	private TransactionService service;

	@BeforeEach
	void setup() {
		service = new TransactionServiceImpl(new TransactionRepository(), new TransactionMapper());
	}

	private TransactionRequestDTO buildDTO(String type, BigDecimal amount, LocalDate date) {
		TransactionRequestDTO dto = new TransactionRequestDTO();
		dto.setType(type);
		dto.setTitle("Test Transaction");
		dto.setAmount(amount);
		dto.setDate(date);
		dto.setDescription("Sample desc");
		return dto;
	}

	@Test
	void createTransaction_success() {
		TransactionRequestDTO dto = buildDTO("CREDIT", BigDecimal.valueOf(500), LocalDate.now());
		Transaction t = service.create(dto);

		assertNotNull(t.getId());
		assertEquals("CREDIT", t.getType());
	}

	@Test
	void getById_success() {
		Transaction t = service.create(buildDTO("CREDIT", BigDecimal.valueOf(1000), LocalDate.now()));
		Transaction found = service.findById(t.getId());

		assertEquals(t.getId(), found.getId());
	}

	@Test
	void getById_notFound() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
	}

	@Test
	void update_success() {
		Transaction t = service.create(buildDTO("DEBIT", BigDecimal.valueOf(300), LocalDate.now()));

		TransactionRequestDTO updated = buildDTO("CREDIT", BigDecimal.valueOf(900), LocalDate.now());
		Transaction updatedTxn = service.update(t.getId(), updated);

		assertEquals("CREDIT", updatedTxn.getType());
		assertEquals(BigDecimal.valueOf(900), updatedTxn.getAmount());
	}

	@Test
	void update_notFound() {
		assertThrows(ResourceNotFoundException.class,
				() -> service.update(555L, buildDTO("DEBIT", BigDecimal.valueOf(10), LocalDate.now())));
	}

	@Test
	void delete_success() {
		Transaction t = service.create(buildDTO("CREDIT", BigDecimal.valueOf(100), LocalDate.now()));
		assertDoesNotThrow(() -> service.delete(t.getId()));
	}

	@Test
	void delete_notFound() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(444L));
	}

	@Test
	void filter_byType_success() {
		service.create(buildDTO("CREDIT", BigDecimal.valueOf(1000), LocalDate.now()));
		service.create(buildDTO("DEBIT", BigDecimal.valueOf(500), LocalDate.now()));

		List<Transaction> result = service.filter("CREDIT", null, null, null, null, null, null);

		assertEquals(1, result.size());
		assertEquals("CREDIT", result.get(0).getType());
	}

	@Test
	void filter_byAmountRange_success() {
		service.create(buildDTO("CREDIT", BigDecimal.valueOf(100), LocalDate.now()));
		service.create(buildDTO("CREDIT", BigDecimal.valueOf(500), LocalDate.now()));

		List<Transaction> result = service.
				filter(null, null, null, 200.0, 600.0, null, null);

		assertEquals(1, result.size());
		assertEquals(BigDecimal.valueOf(500), result.get(0).getAmount());
	}

	@Test
	void findByDate_success() {
		LocalDate today = LocalDate.now();

		service.create(buildDTO("CREDIT", BigDecimal.valueOf(300), today));
		service.create(buildDTO("CREDIT", BigDecimal.valueOf(400), today.minusDays(1)));

		List<Transaction> result = service.findByDate(today);
		assertEquals(1, result.size());
	}

	@Test
	void balance_success() {
		service.create(buildDTO("CREDIT", BigDecimal.valueOf(1000), LocalDate.now()));
		service.create(buildDTO("DEBIT", BigDecimal.valueOf(400), LocalDate.now()));

		var balance = service.getBalance();

		assertEquals(BigDecimal.valueOf(1000), balance.getTotalCredit());
		assertEquals(BigDecimal.valueOf(400), balance.getTotalDebit());
		assertEquals(BigDecimal.valueOf(600), balance.getNetBalance());
	}
}
