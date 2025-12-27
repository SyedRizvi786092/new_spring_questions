package com.employee.dto;

public record EmployeeRequestDTO(
		String name,
		String email,
		Integer age,
		String department,
		Double salary,
		String panNumber) {
	
}
