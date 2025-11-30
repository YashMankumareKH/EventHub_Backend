package com.cdac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.dto.RegisteredUserDto;
import com.cdac.entities.Event;
import com.cdac.entities.EventRegistration;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;

@Repository
public interface RegistrationRepo extends JpaRepository<EventRegistration, Long>{
	
	@Query("""
	        SELECT new com.cdac.dto.RegisteredUserDto(
	            u.id,u.firstName, u.lastName, u.emailId, u.phone)
	        FROM EventRegistration r
	        JOIN r.userDetails u
	        WHERE r.event.id = :eventId
	          AND r.status = :status
	    """)
	    List<RegisteredUserDto> findRegisteredUsersByEventIdAndStatus(
	            @Param("eventId") Long eventId,
	            @Param("status") RegStatus status
	    );

	EventRegistration findByEventAndUserDetails(Event event, User user);
	
	

}
