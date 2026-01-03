package com.example.EventManagment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EventManagment.entity.Event;
@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
//	List<Event> findByDateBetweenOrderByDateAscIdAsc(LocalDate start, LocalDate end);
}
