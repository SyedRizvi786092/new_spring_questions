package com.example.EventManagment.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.EventManagment.dto.RequestDto;
import com.example.EventManagment.dto.ResponseDto;
@Service
public interface EventService {

	ResponseDto create(RequestDto request);

	List<ResponseDto> getAllSortedByDateThenId();

	ResponseDto getById(Long id);

	void deleteById(Long id);

	List<ResponseDto> upcomingEvents(LocalDate startDate, LocalDate endDate);
}
