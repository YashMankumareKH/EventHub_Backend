package com.cdac.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cdac.entities.Event;
import com.cdac.entities.EventStatus;
import com.cdac.repo.EventRepository;

@Service
public class EventStatusScheduler {

    @Autowired
    private EventRepository eventRepository;

    @Scheduled(fixedRate = 60000) // runs every 1 min
    public void autoCompleteEvents() {
        LocalDateTime now = LocalDateTime.now();

        List<Event> events = eventRepository.findByStatusAndEndOnBefore(
                EventStatus.PUBLISHED,
                now
        );

        events.forEach(event -> event.setStatus(EventStatus.COMPLETED));

        eventRepository.saveAll(events);
    }
}


