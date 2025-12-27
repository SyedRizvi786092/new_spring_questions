package com.employee.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Repo.EmployeeRepository;
import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

@RestController
@RequestMapping("/usingCollections")
public class ControllerWithRespository {

	//private List<EmployeeRequestDTO> requestDtoList = new ArrayList<>();
	private final EmployeeRepository empRepository;
	
	ControllerWithRespository(EmployeeRepository empRepository){
		this.empRepository = empRepository;
	}
	
	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> create(@RequestBody EmployeeRequestDTO req) {
		//requestDtoList.add(req);
		EmployeeResponseDTO responseDto =
				//empRepository.getEmployeeID() + 1
				new EmployeeResponseDTO(empRepository.getEmployeeID() + 1, req.name(), req.email(),
						req.age(), req.department(), new Date(), req.salary(), req.panNumber());
		empRepository.emplist.add(responseDto);
		
		return new ResponseEntity<EmployeeResponseDTO>(responseDto,
				HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
		return new ResponseEntity<EmployeeResponseDTO>(empRepository.findById(id),
				HttpStatus.OK);
	}
	
	
}
