package com.cdac.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@AttributeOverride(name = "id", column = @Column(name = "event_id"))
@Getter
@Setter
@NoArgsConstructor
public class Event extends BaseEntity {

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String organization;

    @Column(length = 300)
    private String description;

    private String city;
    private String venue;

    private LocalDateTime startOn;
    private LocalDateTime endOn;

    private int capacity;
    private int price;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "organizer_id") 
    private User userDetails;

    @ManyToOne
    @JoinColumn(name = "category_id") 
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<EventRegistration> registrations;

    // Correct getter so foreign keys work
    public Long getEventId() {
        return this.getId();
    }
}
