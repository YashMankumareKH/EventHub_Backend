package com.cdac.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.dto.EventList;
import com.cdac.entities.EventStatus;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;
import com.cdac.entities.UserRole;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	Optional<User> findByEmailIdAndPassword(String emailId, String password);

	Optional<User> findByEmailId(String email);

	boolean existsByEmailId(String emailId);
	
	boolean existsByPhone(Long phone);
	
	Optional<User> findUserByEmailId(String emailId);
	
//	(Long id, String title, String organization, String city, int price, LocalDateTime startOn)
	@Query("""
			SELECT new com.cdac.dto.EventList(
	            e.id,e.title, e.organization, e.city, e.price , e.startOn)
	        FROM EventRegistration r
	        JOIN r.userDetails u
	        JOIN r.event e
	        WHERE r.userDetails.id = :userId
	          AND r.status = :status
	          
			""")
	List<EventList> findByEventsByUserIdAndStatus(@Param("userId") Long userId,@Param("status") RegStatus status );

	
	@Query("""
			SELECT new com.cdac.dto.EventList(
	            e.id,e.title, e.organization, e.city, e.price , e.startOn)
	        FROM EventRegistration r
	        JOIN r.userDetails u
	        JOIN r.event e
	        WHERE r.userDetails.id = :userId
	          AND r.status = :status
	          AND r.event.status = :eventStatus
	          
			""")
	List<EventList> findByEventsByUserIdAndEventStatus(@Param("userId") Long userId,@Param("status") RegStatus status,@Param("eventStatus") EventStatus eventStatus );

	List<User> findByRole(UserRole roleParticipant);
	
	
	
	
	
}
