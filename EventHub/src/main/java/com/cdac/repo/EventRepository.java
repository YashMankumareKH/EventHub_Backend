package com.cdac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Event;
import com.cdac.entities.EventStatus;
import com.cdac.entities.User;



public interface EventRepository extends JpaRepository<Event,Long> {

	List<Event> findByUserDetailsAndIsActive(User userDetails,boolean isActive);
	
	List<Event> findByStatusAndIsActive(EventStatus status,boolean isActive);
	
	
}
