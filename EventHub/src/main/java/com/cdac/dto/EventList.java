package com.cdac.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventList {
	
	private Long id;
	private String title;
	private String organization;
	private String city;
	private int price;
	private LocalDateTime startOn;
	public EventList(Long id, String title, String organization, String city, int price, LocalDateTime startOn) {
		super();
		this.id = id;
		this.title = title;
		this.organization = organization;
		this.city = city;
		this.price = price;
		this.startOn = startOn;
	}

}
