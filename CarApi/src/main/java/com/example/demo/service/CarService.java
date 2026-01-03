package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CarResponseDto;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;

import jakarta.transaction.Transactional;

@Service
//@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponseDto> getCarByType(String type) {
        return carRepository.findByTypeIgnoreCase(type)
                .stream().map(this::toDto).collect(Collectors.toList());
    }//not found if empty list 

    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private CarResponseDto toDto(Car car) {
        return new CarResponseDto(car.getId(), car.getType(), car.getModel(), car.getTitle());
    }
}
