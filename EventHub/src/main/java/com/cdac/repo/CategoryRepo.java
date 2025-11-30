package com.cdac.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}

