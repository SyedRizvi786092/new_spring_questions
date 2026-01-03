package com.example.EventManagment.dto;

import com.example.EventManagment.entity.Event;

public class EventMapper {

	public static Event toEntity(RequestDto dto) {
		if (dto == null)
			return null;
		Event e = new Event();
		e.setName(dto.getName());
		e.setDate(dto.getDate());
		return e;
	}

	public static ResponseDto toResponse(Event e) {
		if (e == null)
			return null;
		return new ResponseDto(e.getId(), e.getName(), e.getDate());
	}
}
