package com.example.EventManagment.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.EventManagment.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalError {

	private ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest req) {
		ApiError error = new ApiError(OffsetDateTime.now(), status.value(), status.getReasonPhrase(), message,
				req.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	// 404 - Resource Not Found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
	}

	// 400 - Invalid Data
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ApiError> handleInvalidData(InvalidDataException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
	}

	// 400 - Bad body (e.g., empty, malformed JSON)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, "Invalid request body", req);
	}

	// 400 - Validation errors (if used)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
		String msg = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> fe.getField() + " " + fe.getDefaultMessage()).findFirst().orElse("Validation failed");
		return build(HttpStatus.BAD_REQUEST, msg, req);
	}

	// 400 - type mismatch (e.g., wrong date format)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, "Invalid parameter: " + ex.getName(), req);
	}
}
