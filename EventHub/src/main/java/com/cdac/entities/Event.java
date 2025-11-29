package com.cdac.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
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
import lombok.ToString;

@Entity
@Table(name = "events")
@AttributeOverride(name = "id", column = @Column(name = "event_id"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"userDetails", "category", "registrations"})
public class Event extends BaseEntity {

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String organization;

    @Column(length = 300)
    private String description;

    @Column(length = 50)
    private String city;

    @Column(length = 200)
    private String venue;

    @Column(name = "start_on")
    private LocalDateTime startOn;

    @Column(name = "end_on")
    private LocalDateTime endOn;

    private int capacity;

    private int price;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User userDetails;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // FIX 1: MAKE LAZY (required)
    // FIX 2: ADD JSON IGNORE (prevent infinite recursion)
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EventRegistration> registrations;


    public Event(String title, String organization, String description, String city, String venue,
                 LocalDateTime startOn, LocalDateTime endOn, int capacity, int price, EventStatus PUBLISHED) {

        this.title = title;
        this.organization = organization;
        this.description = description;
        this.city = city;
        this.venue = venue;
        this.startOn = startOn;
        this.endOn = endOn;
        this.capacity = capacity;
        this.price = price;
        this.isActive = true;
        this.status = PUBLISHED;
    }

}
