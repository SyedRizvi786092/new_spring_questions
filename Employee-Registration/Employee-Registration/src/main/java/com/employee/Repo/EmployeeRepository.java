package com.employee.Repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.employee.dto.EmployeeResponseDTO;

@Repository
public class EmployeeRepository {
	public static List<EmployeeResponseDTO> emplist = new ArrayList<>();

	static {

		emplist.add(new EmployeeResponseDTO(1L, "Varenya", "varenya@gmail.com", 21, ".net", new Date(), 50000.0,
				"ABCDE1234F"));
		emplist.add(new EmployeeResponseDTO(2L, "Aman", "aman@gmail.com", 22, "Cyber Security", new Date(), 100000.00,
				"GHIJK1234L"));
		emplist.add(new EmployeeResponseDTO(3L, "Sarthak", "sarthakTupkar411@gmail.com", 21, "Java FullStack",
				new Date(), 75000.0, "1234567891"));
		emplist.add(new EmployeeResponseDTO(4L, "Anurag", "anurag@gmail.com", 22, "Java Backend", new Date(), 45000.0,
				"FIEPR3771Q"));
		emplist.add(new EmployeeResponseDTO(5L, "Bhushan", "bhushanjikar28@gmail.com", 22, "C#", new Date(), 25000.0,
				"BAJDE1234N"));

	}

	public List<EmployeeResponseDTO> getAllEmployee() {
		return emplist;
	}

	public Long getEmployeeID() {
		Long new_id = (long) (emplist.size() - 1);
		return new_id + 1;
	}

	public EmployeeResponseDTO findById(Long id) {
		for (EmployeeResponseDTO m : emplist) {
			if (m.getEmployeeId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public List<EmployeeResponseDTO> deleteById(Long id) {
		List<EmployeeResponseDTO> list = emplist.stream().filter((e) -> e.getEmployeeId() != id).toList();
		return list;
	}
}
