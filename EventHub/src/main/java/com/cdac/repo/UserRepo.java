package com.cdac.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	
	Optional<User> findByEmailAndPassword(String em,String pass);
	boolean existsByEmail(String email);
	Optional<User> findUserByEmailId(String emailId);
	
}
