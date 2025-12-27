package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Exception.ExceptionValidator;
import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.dto.ValidationMessage;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService eService;
	
	@PostMapping("/api/employees")
	public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) throws BindException{
		ExceptionValidator validator = new ExceptionValidator();
		WebDataBinder binder = new WebDataBinder(employeeRequestDTO);
		binder.setValidator(validator);
//		BindingResult result = binder.getBindingResult();
//		if(result.hasErrors()) {
//			List<ValidationMessage> errors = result.getAllErrors().stream().map(e -> new ValidationMessage(e.getObjectName(), e.getDefaultMessage())).toList();
//			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//		}
		binder.validate();
		binder.close();
		EmployeeResponseDTO EmployeeDTO = eService.addEmployee(employeeRequestDTO);
		return new ResponseEntity<EmployeeResponseDTO>(EmployeeDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("api/employees")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployee(){
		List<EmployeeResponseDTO> li = eService.getAll();
		return new ResponseEntity<List<EmployeeResponseDTO>>(li, HttpStatus.OK);
	}
	
	@GetMapping("api/employees/{id}")
	public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@PathVariable Long id){
		EmployeeResponseDTO employee = eService.getById(id);
		if(employee != null) {
		return new ResponseEntity<EmployeeResponseDTO>(employee, HttpStatus.OK);
		}else {
			return new ResponseEntity<EmployeeResponseDTO>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@DeleteMapping("api/employees/{id}")
	public ResponseEntity<List<EmployeeResponseDTO>> deleteById(@PathVariable Long id){
		List<EmployeeResponseDTO> li = eService.deleteById(id);
		if(li != null) {
			return new ResponseEntity<List<EmployeeResponseDTO>>(li, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}
	}

}
