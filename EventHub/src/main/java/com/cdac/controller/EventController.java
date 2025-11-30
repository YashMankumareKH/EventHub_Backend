package com.cdac.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.EventList;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.dto.Eventdto;
import com.cdac.entities.Event;
import com.cdac.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class EventController {
		@Autowired
		private  EventService eventService;
		
	
		//List all published events (user) 
		@GetMapping
		public List<EventList> getAllEvents() {
			System.out.println("Get all");
			return eventService.getAllEvents();
		}
		
		//Get event details
		@GetMapping("/{id}")
	    public Eventdto getEventById(@PathVariable Long id) {
	    	
	    	return eventService.getEventById(id);
	    }
		
		
		//Create a new event (Manager only)
		@PostMapping
		public String addEvent(@RequestBody Eventdto event ){
			return eventService.addEvent(event);
		}
		
		
		//Update event (only owner manager)
		@PutMapping
		public String updateEventDetails(@RequestBody Eventdto eventdto ) {
			
			return eventService.updateEventDetails(eventdto);
		}
		
		
		//Get all Manager Events
		@GetMapping("/manager/{managerId}")
		public List<Eventdto> listManagerEvent(@RequestParam Long managerId){
			
			return eventService.getUserEvents(managerId);
			
		}
		
		//Delete Event (Manager)
		@PatchMapping("/{eventId}/delete")
		public String deleteEvent(@PathVariable Long eventId) {
			
			
			return eventService.deleteEvent(eventId);
		}
		
		
		//Registrations of particular Event
		@GetMapping("/{eventId}/attendees")
		public List<RegisteredUserDto> getEventRegistrations(@PathVariable Long eventId) {
			
			return eventService.getEventRegistrations(eventId);
		}
		
		//cancel registration of particular user(Manager)
		@PatchMapping("/{eventId}/attendees/{attendeeId}")
		public String cancelRegistrationOfUser(@PathVariable Long eventId,@PathVariable Long attendeeId ) {
			
			return eventService.cancelRegistrationOfUser(eventId,attendeeId);
		}
		
		
		
		
		
		
		
		
		
	    
		
		
		
}
