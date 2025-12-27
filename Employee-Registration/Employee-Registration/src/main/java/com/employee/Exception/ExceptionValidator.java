package com.employee.Exception;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;

public class ExceptionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EmployeeRequestDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		EmployeeRequestDTO employee = (EmployeeRequestDTO) target;

		String name = employee.name();
		String email = employee.email();
		Integer age = employee.age();
		String department = employee.department();
		Double salary = employee.salary();
		String panNumber = employee.panNumber();
//		String first5Char = employee.getPanNumber().substring(0, 4);
//		String lastNo = employee.getPanNumber().substring(9);
		String regex = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";

		if (name == null || name.isEmpty() || name.length() < 3) {
			errors.rejectValue("name", null, "Name is required");
		}

		if (email == null) {
			errors.rejectValue("email", null, "email is required");
		} else if (!email.contains("@")) {
			errors.rejectValue("email", null, "Should be in valid format");
		}

		if (age == null || age <= 21 && age >= 60) {
			errors.rejectValue("age", null, "Valid Age is required");
		}

		if (department == null) {
			errors.rejectValue("department", null, "Department is Mandatory");
		}

		if (salary == null || salary < 0) {
			errors.rejectValue("salary", null, "Salary must be greater than zero");
		}

		if (panNumber == null) {
			errors.rejectValue("panNumber", null, "Pan Number must be in valid format");
		} else if (regex != panNumber) {
			errors.rejectValue("panNumber", null, "Pan Number must be in valid format");
		}

	}

}
