package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.entities.Event;
import com.cdac.service.EventService;

//import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
		@Autowired
		private  EventService eventService;
		
		public EventController() {
		 System.out.println("In Constructor"+ getClass());
		}
		
		
		@GetMapping
		public List<Event> getAllEvents() {
			System.out.println("Get all");
			return eventService.getAllEvents();
		}
		
		
		
}
