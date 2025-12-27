package com.employee.dto;

import java.util.Date;

public class EmployeeResponseDTO {
	
	private Long employeeId;
	private String name;
	private String email;
	private Integer age;
	private String department;
	private Date joiningDate;
	private Double salary;
	private String panNumber;

	public EmployeeResponseDTO(Long employeeId, String name, String email, Integer age, String department,
			Date joiningDate, Double salary, String panNumber) {
		super();
		this.employeeId=employeeId;
		this.name = name;
		this.email = email;
		this.age = age;
		this.department = department;
		this.joiningDate = joiningDate;
		this.salary = salary;
		this.panNumber = panNumber;

	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	@Override
	public String toString() {
		return "EmployeeResponseDTO [employeeId=" + employeeId + ", name=" + name + ", email=" + email + ", age=" + age
				+ ", department=" + department + ", joiningDate=" + joiningDate + ", salary=" + salary + ", panNumber="
				+ panNumber + "]";
	}

	public EmployeeResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
