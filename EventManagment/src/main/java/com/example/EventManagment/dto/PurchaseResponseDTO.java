package com.example.EventManagment.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
public class PurchaseResponseDTO {
	private Long id;
	private String name;
	private BigDecimal totalPrice;
	public PurchaseResponseDTO() {
		
	}
}
