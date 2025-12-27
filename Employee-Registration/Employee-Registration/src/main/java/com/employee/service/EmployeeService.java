package com.employee.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.EmployeeRegistrationApplication;
import com.employee.Repo.EmployeeRepository;
import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository eRepo;

	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO empDTO) {

		Long id = eRepo.getEmployeeID();
		EmployeeResponseDTO responseDTOObj = new EmployeeResponseDTO();
		responseDTOObj.setEmployeeId(id + 1);
		responseDTOObj.setName(empDTO.name());
		responseDTOObj.setEmail(empDTO.email());
		responseDTOObj.setAge(empDTO.age());
		responseDTOObj.setDepartment(empDTO.department());
		responseDTOObj.setJoiningDate(new Date());
		responseDTOObj.setSalary(empDTO.salary());
		responseDTOObj.setPanNumber(empDTO.panNumber());
		eRepo.getAllEmployee().add(responseDTOObj);

		return responseDTOObj;

	}
	
	public List<EmployeeResponseDTO> getAll() {
		return eRepo.getAllEmployee();	
	}
	
	public EmployeeResponseDTO getById(Long id) {
		EmployeeResponseDTO emp=  eRepo.findById(id);
		if(emp == null) {
			return null;
		}else {
			return emp;
		}
	}
	
	public List<EmployeeResponseDTO> deleteById(Long id) {
		List<EmployeeResponseDTO> list = eRepo.deleteById(id);
		return list;
	}
}
