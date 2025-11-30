package com.cdac.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventResponseDTO {
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
	    private String status;
	    private boolean isActive;
	    private String categoryName;
	    private Long categoryId;

	    private Long organizerId;
	    private String organizerName;
}
