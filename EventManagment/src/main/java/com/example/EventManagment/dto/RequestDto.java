package com.example.EventManagment.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestDto {

	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public RequestDto() {
	}

	public RequestDto(String name, LocalDate date) {
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
