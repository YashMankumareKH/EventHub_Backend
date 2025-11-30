package com.cdac.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventList {
	
	private String title;
	private String organization;
	private String city;
	private int price;
	private LocalDateTime startOn;

}
