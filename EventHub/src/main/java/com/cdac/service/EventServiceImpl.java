package com.cdac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.entities.Event;
import com.cdac.repo.EventRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository; 
	
	@Override
	public List<Event> getAllEvents() {
		
		return eventRepository.findAll();
	}

}
