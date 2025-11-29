package com.cdac.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "event_registration")
@AttributeOverride(name = "id" , column = @Column(name = "registration_id"))
@AttributeOverride(name = "createdOn" , column = @Column(name = "reg_datetime"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true,exclude = {"userDetails"})
public class EventRegistration extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
	private RegStatus status;
	
	@ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User userDetails;

	public EventRegistration(RegStatus status) {
		super();
		this.status = status;
		
	}
	
	

}
