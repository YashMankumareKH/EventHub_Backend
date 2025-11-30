package com.cdac.service;

import java.util.List;

import com.cdac.dto.EventList;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.dto.Eventdto;
import com.cdac.entities.Event;

public interface EventService {
		
	List<EventList> getAllEvents();

	String addEvent(Eventdto event); 
	
	List<Eventdto> getUserEvents(Long id);

	Eventdto getEventById(Long id);

	String updateEventDetails(Eventdto eventdto);

	String deleteEvent(Long id);

	List<RegisteredUserDto> getEventRegistrations(Long eventId);

	String cancelRegistrationOfUser(Long eventId, Long attendeeId);
	
}
