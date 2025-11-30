package com.cdac.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventRequestDTO {
	  private String title;
	    private String organization;
	    private String description;
	    private String city;
	    private String venue;
	    private LocalDateTime startOn;
	    private LocalDateTime endOn;
	    private int capacity;
	    private int price;
	    private Long organizerId;
	    private Long categoryId;
	    private String status;

}
