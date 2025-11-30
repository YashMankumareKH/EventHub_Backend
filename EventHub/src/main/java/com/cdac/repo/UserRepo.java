package com.cdac.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	Optional<User> findByEmailIdAndPassword(String emailId, String password);

	Optional<User> findByEmailId(String email);

	boolean existsByEmailId(String emailId);
	
	boolean existsByPhone(Long phone);
	
	Optional<User> findUserByEmailId(String emailId);
	
}
