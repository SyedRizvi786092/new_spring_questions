package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CarResponseDto;
import com.example.demo.service.CarService;
@RestController//sher

public class CarController {
	@Autowired
	private CarService carService;
	@GetMapping("api/cars/available")//sher
	public ResponseEntity<?> getCarByType(@RequestParam(required = true) LocalDate startDate, @RequestParam(required = true) LocalDate endDate, @RequestParam(required = false) String type){
    	LocalDate today = LocalDate.now(); //sher
    	Map<String, Object> errorMap = new HashMap<>();
    	if(startDate.isAfter(endDate)) {
    		errorMap.put("status", 400);//400 after before even hasmap bhi khud likhna hai
    		errorMap.put("error", "INVALID_START_DATE");
    		errorMap.put("message", "Start date cannot be after end date.");
    		
    		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    	}
    	if(startDate.isBefore(today)) {
    		errorMap.put("status", 400);
    		errorMap.put("error", "INVALID_START_DATE");
    		errorMap.put("message", "Start date cannot be before today.");
    		
    		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    	}
    	List<CarResponseDto> res = carService.getCarByType(type); //
    	return new ResponseEntity<>(res, HttpStatus.OK); //status code 
    	//sher
    }
}
