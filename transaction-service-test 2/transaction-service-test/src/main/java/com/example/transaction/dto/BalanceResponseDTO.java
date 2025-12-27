package com.example.transaction.dto;

import java.math.BigDecimal;

public class BalanceResponseDTO {
	private BigDecimal totalCredit;
	private BigDecimal totalDebit;
	private BigDecimal netBalance;

	public BalanceResponseDTO(BigDecimal totalCredit, BigDecimal totalDebit) {
		this.totalCredit = totalCredit;
		this.totalDebit = totalDebit;
		this.netBalance = totalCredit.subtract(totalDebit);
	}

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit;
	}

	public BigDecimal getNetBalance() {
		return netBalance;
	}
}
