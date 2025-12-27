package com.example.transaction.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

public class TransactionRequestDTO {
	
	@Pattern(regexp="CREDIT|DEBIT|REFUND")
	private String type;
	
	@NotBlank
	@Length(min=3, max=50)
	private String title;
	@Positive
	@DecimalMin(value = "0.01")
	@Digits(integer = Integer.MAX_VALUE,fraction=2)
	private BigDecimal amount;
	
	@NotNull(message = "date mandatory")
	
	@PastOrPresent(message = "cannot be future date")
	private LocalDate date;
	
	@Length(max=200)
	private String description;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
