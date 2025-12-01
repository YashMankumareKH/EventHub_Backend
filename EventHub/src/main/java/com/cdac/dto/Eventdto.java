package com.cdac.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Eventdto {
	
		private Long id;
	    private String title;
	    private String organization;
	    private String description;
	    private String city;
	    private String venue;
	    private LocalDateTime startOn;
	    private LocalDateTime endOn;
	    private int capacity;
	    private int price;
	    private String categoryName;
	    private Long categoryId;
	    private Long managerId;
	  

}
