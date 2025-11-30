package com.cdac.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Category;
import com.cdac.entities.User;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String categoryName);
}

