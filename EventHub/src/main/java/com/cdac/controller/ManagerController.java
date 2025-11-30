package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.entities.Event;
import com.cdac.service.EventService;

@RestController
@RequestMapping("/manager")
public class ManagerController { 
	
	@Autowired
	private EventService eventService;
	
	
	
	
	
	

}
