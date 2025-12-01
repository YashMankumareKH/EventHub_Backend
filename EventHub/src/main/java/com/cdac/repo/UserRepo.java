package com.cdac.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	Optional<User> findByEmailIdAndPassword(String emailId, String password);

	Optional<User> findByEmailId(String email);

	boolean existsByEmailId(String emailId);
	
	boolean existsByPhone(Long phone);
	
	Optional<User> findUserByEmailId(String emailId);
	
////	(Long id, String title, String organization, String city, int price, LocalDateTime startOn)
//	@Query("""
//			SELECT new com.cdac.dto.EventList(
//	            e.id,e.title, e.organization, e.city, e.price , e.startOn)
//	        FROM EventRegistration r
//	        JOIN r.userDetails u
//	        JOIN r.event e
//	        WHERE r.userDetails.id = :userId
//	          AND r.status = :status
//	          AND r.user
//			
//			""");
	
}
