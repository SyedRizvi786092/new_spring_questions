package com.example.EventManagment.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.EventManagment.dto.EventMapper;
import com.example.EventManagment.dto.RequestDto;
import com.example.EventManagment.dto.ResponseDto;
import com.example.EventManagment.entity.Event;
import com.example.EventManagment.exception.InvalidDataException;
import com.example.EventManagment.exception.ResourceNotFoundException;
import com.example.EventManagment.repository.EventRepo;

@Service
public class EventServiceImpl implements EventService {

	private final EventRepo repo;

	public EventServiceImpl(EventRepo repo) {
		this.repo = repo;
	}

	@Override
	public ResponseDto create(RequestDto request) {
		// Invalid body handled at GlobalExceptionHandler (null body)
		// Per spec: ResourceNotFound 404 when name is null
		if (request.getName() == null) {
			throw new ResourceNotFoundException("Name is null");
		}
		// Invalid data 400 when no data in body -> if date null, treat as invalid data
		if (request.getDate() == null) {
			throw new InvalidDataException("Date must not be null (yyyy-MM-dd)");
		}
		Event saved = repo.save(EventMapper.toEntity(request));
		return EventMapper.toResponse(saved);
	}

	@Override
	public List<ResponseDto> getAllSortedByDateThenId() {
		List<Event> events = repo.findAll(Sort.by(Sort.Direction.ASC, "date").and(Sort.by(Sort.Direction.ASC, "id")));
		return events.stream().map(EventMapper::toResponse).toList();
	}

	@Override
	public ResponseDto getById(Long id) {
		Event e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		return EventMapper.toResponse(e);
	}

	@Override
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException("Event not found with id: " + id);
		}
		repo.deleteById(id);
	}

	@Override
	public List<ResponseDto> upcomingEvents(LocalDate startDate, LocalDate endDate) {
		if (startDate == null || endDate == null) {
			throw new InvalidDataException("startDate and endDate required (yyyy-MM-dd)");
		}
		List<Event> events = repo.findByDateBetweenOrderByDateAscIdAsc(startDate, endDate);
		return events.stream().map(EventMapper::toResponse).toList(); // empty list if none
	}
}
