package com.cdac.service;

import java.util.List;

import com.cdac.dto.EventList;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.dto.Eventdto;
import com.cdac.entities.Event;

public interface EventService {
		
	List<EventList> getAllEvents();

	String addEvent(Long managerId, Eventdto event); 
	
	List<Eventdto> getUserEvents(Long id);

	Eventdto getEventById(Long id);

	String updateEventDetails(Eventdto eventdto);

	String deleteEvent(Long id);

	List<RegisteredUserDto> getEventRegistrations(Long eventId);

	String cancelRegistrationOfUser(Long eventId, Long attendeeId);

	String cancelEvent(Long eventId);

	String registerForEvent(Long userId, Long eventId);

	String cancelRegistrationForEvent(Long userId, Long eventId);

	List<Event> getEvents();

	List<Event> getAllEventsOfManager(Long managerId);

	String deleteManagerEvent(Long eventId);
	
}
