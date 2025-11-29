package com.cdac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.EventRegistration;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    // use event.id 
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    // For listing users registrations
    List<EventRegistration> findByUserId(Long userId);

    // For listing registrations of an event
    List<EventRegistration> findByEventId(Long eventId);
}
