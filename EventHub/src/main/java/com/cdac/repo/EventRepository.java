package com.cdac.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.Event;

public interface EventRepository extends JpaRepository<Event,Long> {

}
