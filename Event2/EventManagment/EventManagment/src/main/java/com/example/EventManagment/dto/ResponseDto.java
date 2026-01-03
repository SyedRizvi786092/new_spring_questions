package com.example.EventManagment.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseDto {

	private Long id;
	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public ResponseDto() {
	}

	public ResponseDto(Long id, String name, LocalDate date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
