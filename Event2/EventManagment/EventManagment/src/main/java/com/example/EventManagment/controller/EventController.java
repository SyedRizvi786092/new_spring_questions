package com.example.EventManagment.controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventManagment.dto.DeleteResponseDto;
import com.example.EventManagment.dto.RequestDto;
import com.example.EventManagment.dto.ResponseDto;
import com.example.EventManagment.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    // Create -> 201
    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody RequestDto request) {
        ResponseDto created = service.create(request);
        return ResponseEntity.status(201).body(created);
    }

    // Get All (sorted by date then id) -> 200
    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAll() {
        List<ResponseDto> list = service.getAllSortedByDateThenId();
        return ResponseEntity.ok(list);
    }

    // Get by id -> 200
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        ResponseDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    // Delete -> 204 (return DeleteResponseDto; per spec)
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDto> delete(@PathVariable Long id) {
        service.deleteById(id);
        DeleteResponseDto body = new DeleteResponseDto("Deleted id " + id);
        return ResponseEntity.status(204).body(body);
    }

    // Upcoming events in [startDate, endDate] -> 200
    @GetMapping("/upcoming")
    public ResponseEntity<List<ResponseDto>> upcoming(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate")   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<ResponseDto> list = service.upcomingEvents(startDate, endDate);
        return ResponseEntity.ok(list);
    }
}


