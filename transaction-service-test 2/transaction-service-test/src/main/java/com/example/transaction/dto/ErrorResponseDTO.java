package com.example.transaction.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {
	private LocalDateTime timestamp;
	private int statusCode;
	private String message;
	private List<String> details;

	public ErrorResponseDTO(LocalDateTime timestamp, int statusCode, String message, List<String> details) {
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getDetails() {
		return details;
	}
}
