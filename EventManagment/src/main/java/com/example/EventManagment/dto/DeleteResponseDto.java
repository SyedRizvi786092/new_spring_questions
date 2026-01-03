package com.example.EventManagment.dto;

public class DeleteResponseDto {
	private String message;

	public DeleteResponseDto() {
	}

	public DeleteResponseDto(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
