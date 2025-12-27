package com.employee.Exception;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.dto.ValidationMessage;

@ControllerAdvice
public class EmployeeExceptionHandler {

	@ExceptionHandler(value = {BindException.class})
	public ResponseEntity<?> exception(BindException e){
		List<ValidationMessage> errors = e.getBindingResult().getFieldErrors().stream().map(ex -> 
						new ValidationMessage(ex.getField(), ex.getDefaultMessage())).toList();
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
