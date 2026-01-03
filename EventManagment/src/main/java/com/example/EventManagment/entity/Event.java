package com.example.EventManagment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Name can be null (per spec: 404 when name is null on create)
	@Column(name = "name")
	private String name;
	
	private String venue;

	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	private Integer availableTickets;
	
	private BigDecimal price;


	public Event(Long id, String name, LocalDate date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}

	// getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
