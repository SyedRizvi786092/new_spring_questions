package com.example.transaction.exception;

import com.example.transaction.dto.ErrorResponseDTO;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ErrorResponseDTO(LocalDateTime.now(), 404, ex.getMessage(), null),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
		
		return new ResponseEntity<>(new ErrorResponseDTO(LocalDateTime.now(), 400, "Validation Failed", null),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleOthers(Exception ex) {
		return null;
	}
}
