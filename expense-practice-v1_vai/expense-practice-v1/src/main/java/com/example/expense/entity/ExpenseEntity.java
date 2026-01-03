
package com.example.expense.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * TODO: Add Jakarta Bean Validation annotations here, matching the specs.
 * - @NotBlank and @Size for title - @Size for description - @NotNull
 * and @Positive for amount - @NotBlank and @Size(min=3, max=3) for currency
 * - @NotNull for timestamp
 */
@Entity
@Table(name = "expenses")
public class ExpenseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@NotBlank
	@Size
	@Column(nullable = false, length = 100)
	private String title;
    
	@Size
	@Column(length = 255)
	private String description;
	
	@NotNull
    @Positive
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;
    
	@NotBlank
	@Size(min=3,max=3)
	@Column(nullable = false, length = 3)
	private String currency;
    
	@NotNull
	@Column(nullable = false)
	private LocalDateTime timestamp;

	public ExpenseEntity() {
	}

	public ExpenseEntity(Long id, String title, String description, BigDecimal amount, String currency,
			LocalDateTime timestamp) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
